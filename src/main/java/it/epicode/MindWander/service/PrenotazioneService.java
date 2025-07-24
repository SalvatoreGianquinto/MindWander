package it.epicode.MindWander.service;

import it.epicode.MindWander.dto.PrenotazioneDto;
import it.epicode.MindWander.dto.PrenotazioneViewDto;
import it.epicode.MindWander.exception.NotFoundException;
import it.epicode.MindWander.exception.UnAuthorizedException;
import it.epicode.MindWander.model.Prenotazione;
import it.epicode.MindWander.model.Stanza;
import it.epicode.MindWander.model.User;
import it.epicode.MindWander.repository.PrenotazioneRepository;
import it.epicode.MindWander.repository.StanzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {
    private final PrenotazioneRepository prenotazioneRepository;
    private final StanzaRepository stanzaRepository;

    public Prenotazione creaPrenotazione(PrenotazioneDto prenotazioneDto, User user) {
        Stanza stanza = stanzaRepository.findById(prenotazioneDto.getStanzaId())
                .orElseThrow(() -> new NotFoundException("Stanza non trovata"));

        if (prenotazioneDto.getDataInizio().isAfter(prenotazioneDto.getDataFine())) {
            throw new NotFoundException("La data di inizio deve essere prima della data di fine");
        }

        List<Prenotazione> sovrapposte = prenotazioneRepository.findOverlappingPrenotazioni(
                prenotazioneDto.getStanzaId(),
                prenotazioneDto.getDataInizio(),
                prenotazioneDto.getDataFine());

        if (!sovrapposte.isEmpty()) {
            throw new NotFoundException("Stanza non disponibile nelle date selezionate");
        }

        Prenotazione p = new Prenotazione();
        p.setUser(user);
        p.setStanza(stanza);
        p.setNumeroOspiti(prenotazioneDto.getNumeroOspiti());
        p.setNote(prenotazioneDto.getNote());
        p.setDataInizio(prenotazioneDto.getDataInizio());
        p.setDataFine(prenotazioneDto.getDataFine());

        return prenotazioneRepository.save(p);
    }

    public Prenotazione modificaPrenotazione(Long id, PrenotazioneDto dto, User user) {
        Prenotazione prenotazione = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));

        if (!prenotazione.getUser().getId().equals(user.getId())) {
            throw new UnAuthorizedException("Non puoi modificare questa prenotazione");
        }

        if (dto.getDataInizio().isAfter(dto.getDataFine())) {
            throw new NotFoundException("La data di inizio deve essere prima della data di fine");
        }

        List<Prenotazione> sovrapposte = prenotazioneRepository.findOverlappingPrenotazioni(
                prenotazione.getStanza().getId(),
                dto.getDataInizio(),
                dto.getDataFine());

        boolean overlap = sovrapposte.stream()
                .anyMatch(p -> !p.getId().equals(id));

        if (overlap) {
            throw new NotFoundException("Stanza non disponibile nelle date selezionate");
        }

        prenotazione.setDataInizio(dto.getDataInizio());
        prenotazione.setDataFine(dto.getDataFine());
        prenotazione.setNumeroOspiti(dto.getNumeroOspiti());
        prenotazione.setNote(dto.getNote());

        return prenotazioneRepository.save(prenotazione);
    }

    public void cancellaPrenotazione(Long id, User user) {
        Prenotazione prenotazione = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));

        if (!prenotazione.getUser().getId().equals(user.getId())) {
            throw new UnAuthorizedException("Non puoi cancellare questa prenotazione");
        }

        prenotazioneRepository.delete(prenotazione);
    }

    public List<Prenotazione> trovaPrenotazioniUtente(User user) {
        return prenotazioneRepository.findByUserId(user.getId());
    }

    private PrenotazioneViewDto mapToViewDto(Prenotazione p) {
        PrenotazioneViewDto dto = new PrenotazioneViewDto();
        dto.setId(p.getId());
        dto.setNomeStruttura(p.getStanza().getStruttura().getNome());
        dto.setNomeStanza(p.getStanza().getNome());
        dto.setDataInizio(p.getDataInizio());
        dto.setDataFine(p.getDataFine());
        dto.setNumeroOspiti(p.getNumeroOspiti());
        dto.setPrezzoTotale(p.getPrezzoTotale());
        dto.setNote(p.getNote());
        return dto;
    }

    public List<PrenotazioneViewDto> trovaPrenotazioniUtenteView(User user) {
        List<Prenotazione> prenotazioni = prenotazioneRepository.findByUserId(user.getId());
        return prenotazioni.stream()
                .map(this::mapToViewDto)
                .toList();
    }
}
