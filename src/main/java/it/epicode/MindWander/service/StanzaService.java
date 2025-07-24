package it.epicode.MindWander.service;

import it.epicode.MindWander.dto.StanzaDto;
import it.epicode.MindWander.exception.NotFoundException;
import it.epicode.MindWander.model.Stanza;
import it.epicode.MindWander.model.Struttura;
import it.epicode.MindWander.repository.StanzaRepository;
import it.epicode.MindWander.repository.StrutturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StanzaService {

    private final StanzaRepository stanzaRepository;
    private final StrutturaRepository strutturaRepository;

    public StanzaDto creaStanza(StanzaDto dto) {
        Struttura struttura = strutturaRepository.findById(dto.getStrutturaId())
                .orElseThrow(() -> new NotFoundException("Struttura con ID " + dto.getStrutturaId() + " non trovata"));

        Stanza stanza = new Stanza();
        stanza.setNome(dto.getNome());
        stanza.setPostiLetto(dto.getPostiLetto());
        stanza.setDescrizione(dto.getDescrizione());
        stanza.setStruttura(struttura);

        Stanza saved = stanzaRepository.save(stanza);

        struttura.getStanze().add(saved);
        strutturaRepository.save(struttura);

        return toDto(saved);
    }

    public List<Stanza> getStanzeByStruttura(Long strutturaId) {
        if (!strutturaRepository.existsById(strutturaId)) {
            throw new NotFoundException("Struttura con ID " + strutturaId + " non trovata");
        }
        return stanzaRepository.findByStrutturaId(strutturaId);
    }

    public Stanza getStanzaById(Long id) {
        return stanzaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Stanza non trovata con ID: " + id));
    }

    public StanzaDto aggiornaStanza(Long id, StanzaDto dto) {
        Stanza stanza = stanzaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Stanza non trovata con ID: " + id));

        Struttura struttura = strutturaRepository.findById(dto.getStrutturaId())
                .orElseThrow(() -> new NotFoundException("Struttura con ID " + dto.getStrutturaId() + " non trovata"));

        stanza.setNome(dto.getNome());
        stanza.setPostiLetto(dto.getPostiLetto());
        stanza.setDescrizione(dto.getDescrizione());
        stanza.setStruttura(struttura);

        Stanza updated = stanzaRepository.save(stanza);

        return toDto(updated);
    }

    public void eliminaStanza(Long id) {
        Stanza stanza = stanzaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Stanza non trovata con ID: " + id));
        stanzaRepository.delete(stanza);
    }

    private StanzaDto toDto(Stanza stanza) {
        StanzaDto dto = new StanzaDto();
        dto.setId(stanza.getId());
        dto.setNome(stanza.getNome());
        dto.setDescrizione(stanza.getDescrizione());
        dto.setPostiLetto(stanza.getPostiLetto());
        dto.setStrutturaId(stanza.getStruttura().getId());
        return dto;
    }
}
