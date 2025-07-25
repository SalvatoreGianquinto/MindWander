package it.epicode.MindWander.service;

import it.epicode.MindWander.dto.StanzaDto;
import it.epicode.MindWander.dto.StrutturaDto;
import it.epicode.MindWander.dto.StrutturaResponseDto;
import it.epicode.MindWander.exception.NotFoundException;
import it.epicode.MindWander.model.Recensione;
import it.epicode.MindWander.model.ServizioExtra;
import it.epicode.MindWander.model.Stanza;
import it.epicode.MindWander.model.Struttura;
import it.epicode.MindWander.repository.ServizioExtraRepository;
import it.epicode.MindWander.repository.StrutturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StrutturaService {

    private final StrutturaRepository strutturaRepository;
    private final ServizioExtraRepository servizioExtraRepository;

    public StrutturaResponseDto save(StrutturaDto strutturaDto) {
        Struttura struttura = new Struttura();
        struttura.setNome(strutturaDto.getNome());
        struttura.setDescrizione(strutturaDto.getDescrizione());
        struttura.setCitta(strutturaDto.getCitta());
        struttura.setIndirizzo(strutturaDto.getIndirizzo());
        struttura.setPrezzo(strutturaDto.getPrezzo());
        struttura.setDisponibile(strutturaDto.getDisponibile());
        struttura.setMoodAssociato(strutturaDto.getMoodAssociato());
        struttura.setCategoriaAlloggio(strutturaDto.getCategoriaAlloggio());
        struttura.setImmaginiUrl(strutturaDto.getImmaginiUrl());
        struttura.setServiziExtra(getServiziExtraByIds(strutturaDto.getServiziExtraIds()));

        struttura.setStanze(convertToStanze(strutturaDto.getStanze(), struttura));

        strutturaRepository.save(struttura);
        return convertToResponseDto(struttura);
    }

    public StrutturaResponseDto update(Long id, StrutturaDto dto) {
        Struttura struttura = strutturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Struttura con ID " + id + " non trovata"));

        struttura.setNome(dto.getNome());
        struttura.setDescrizione(dto.getDescrizione());
        struttura.setCitta(dto.getCitta());
        struttura.setIndirizzo(dto.getIndirizzo());
        struttura.setPrezzo(dto.getPrezzo());
        struttura.setDisponibile(dto.getDisponibile());
        struttura.setMoodAssociato(dto.getMoodAssociato());
        struttura.setCategoriaAlloggio(dto.getCategoriaAlloggio());
        struttura.setImmaginiUrl(dto.getImmaginiUrl());
        struttura.setServiziExtra(getServiziExtraByIds(dto.getServiziExtraIds()));


        strutturaRepository.save(struttura);
        return convertToResponseDto(struttura);
    }

    public List<StrutturaResponseDto> findAll() {
        return strutturaRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public StrutturaResponseDto findById(Long id) {
        Struttura struttura = strutturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Struttura con ID " + id + " non trovata"));
        return convertToResponseDto(struttura);
    }

    public void delete(Long id) {
        if (!strutturaRepository.existsById(id)) {
            throw new NotFoundException("Struttura con ID " + id + " non trovata");
        }
        strutturaRepository.deleteById(id);
    }

    public List<StrutturaResponseDto> findWithFiltersSimple(
            String citta,
            String mood,
            Double minPrezzo,
            Double maxPrezzo,
            Double votoMedioMin) {

        List<Struttura> strutture = strutturaRepository.findAll();

        return strutture.stream()
                .filter(s -> citta == null || s.getCitta().equalsIgnoreCase(citta))
                .filter(s -> mood == null || s.getMoodAssociato().equalsIgnoreCase(mood))
                .filter(s -> minPrezzo == null || s.getPrezzo() >= minPrezzo)
                .filter(s -> maxPrezzo == null || s.getPrezzo() <= maxPrezzo)
                .filter(s -> votoMedioMin == null ||
                        (!s.getRecensioni().isEmpty() &&
                                s.getRecensioni().stream()
                                        .mapToInt(Recensione::getVoto)
                                        .average()
                                        .orElse(0) >= votoMedioMin))
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    private StrutturaResponseDto convertToResponseDto(Struttura struttura) {
        StrutturaResponseDto dto = new StrutturaResponseDto();
        dto.setId(struttura.getId());
        dto.setNome(struttura.getNome());
        dto.setDescrizione(struttura.getDescrizione());
        dto.setCitta(struttura.getCitta());
        dto.setIndirizzo(struttura.getIndirizzo());
        dto.setPrezzo(struttura.getPrezzo());
        dto.setDisponibile(struttura.getDisponibile());
        dto.setMoodAssociato(struttura.getMoodAssociato());
        dto.setCategoriaAlloggio(struttura.getCategoriaAlloggio());
        dto.setImmaginiUrl(struttura.getImmaginiUrl());
        dto.setServiziExtra(struttura.getServiziExtra()
                .stream()
                .map(ServizioExtra::getNome)
                .collect(Collectors.toList()));

        dto.setStanze(convertToStanzeDto(struttura.getStanze()));

        return dto;
    }

    private List<Stanza> convertToStanze(List<StanzaDto> stanzeDto, Struttura struttura) {
        if (stanzeDto == null) return List.of();

        return stanzeDto.stream()
                .map(dto -> {
                    Stanza stanza = new Stanza();
                    stanza.setNome(dto.getNome());
                    stanza.setPrezzo(dto.getPrezzo());
                    stanza.setPostiLetto(dto.getPostiLetto());
                    stanza.setStruttura(struttura);
                    return stanza;
                })
                .collect(Collectors.toList());
    }

    private List<StanzaDto> convertToStanzeDto(List<Stanza> stanze) {
        if (stanze == null) return List.of();

        return stanze.stream()
                .map(stanza -> {
                    StanzaDto dto = new StanzaDto();
                    dto.setId(stanza.getId());
                    dto.setNome(stanza.getNome());
                    dto.setPostiLetto(stanza.getPostiLetto());
                    dto.setPrezzo(stanza.getPrezzo());
                    dto.setDescrizione(stanza.getDescrizione());
                    dto.setStrutturaId(stanza.getStruttura() != null ? stanza.getStruttura().getId() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private Set<ServizioExtra> getServiziExtraByIds(List<Long> ids) {
        if (ids == null) return Set.of();

        return ids.stream()
                .map(id -> servizioExtraRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Servizio extra con ID " + id + " non trovato")))
                .collect(Collectors.toSet());
    }
}
