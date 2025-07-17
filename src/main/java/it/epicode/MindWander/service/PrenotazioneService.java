package it.epicode.MindWander.service;

import it.epicode.MindWander.dto.PrenotazioneDto;
import it.epicode.MindWander.exception.NotFoundException;
import it.epicode.MindWander.exception.UnAuthorizedException;
import it.epicode.MindWander.model.Prenotazione;
import it.epicode.MindWander.model.Struttura;
import it.epicode.MindWander.model.User;
import it.epicode.MindWander.repository.PrenotazioneRepository;
import it.epicode.MindWander.repository.StrutturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {
    private final PrenotazioneRepository prenotazioneRepository;
    private final StrutturaRepository strutturaRepository;

    public Prenotazione creaPrenotazione(PrenotazioneDto prenotazioneDto, User user){
        Struttura struttura = strutturaRepository.findById(prenotazioneDto.getStrutturaId())
                .orElseThrow(()-> new NotFoundException("Struttura non trovata"));

        if(prenotazioneDto.getDataInizio().isAfter(prenotazioneDto.getDataFine())) {
            throw new RuntimeException("La data di inizio deve essere prima della data di fine");
        }

        List<Prenotazione> sovrapposte = prenotazioneRepository
                .findByStrutturaIdAndDataFineAfterAndDataInizioBefore(
                        prenotazioneDto.getStrutturaId(),
                        prenotazioneDto.getDataInizio(),
                        prenotazioneDto.getDataFine());

        if (!sovrapposte.isEmpty()) {
            throw new RuntimeException("Struttura non disponibile nelle date selezionate");
        }

        Prenotazione p = new Prenotazione();
        p.setUser(user);
        p.setStruttura(struttura);
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
            throw new NotFoundException("Non puoi modificare questa prenotazione");
        }

        if(dto.getDataInizio().isAfter(dto.getDataFine())) {
            throw new NotFoundException("La data di inizio deve essere prima della data di fine");
        }

        List<Prenotazione> sovrapposte = prenotazioneRepository
                .findByStrutturaIdAndDataFineAfterAndDataInizioBefore(
                        prenotazione.getStruttura().getId(),
                        dto.getDataInizio(),
                        dto.getDataFine());

        boolean overlap = sovrapposte.stream()
                .anyMatch(p -> !p.getId().equals(id));

        if (overlap) {
            throw new NotFoundException("Hai già una prenotazione per quelle data");
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

}
