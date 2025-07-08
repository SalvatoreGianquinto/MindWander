package it.epicode.MindWander.service;

import it.epicode.MindWander.dto.StrutturaDto;
import it.epicode.MindWander.dto.StrutturaResponseDto;
import it.epicode.MindWander.exception.NotFoundException;
import it.epicode.MindWander.model.ServizioExtra;
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

    public void delete(Long id) {
        if (!strutturaRepository.existsById(id)) {
            throw new NotFoundException("Struttura con ID " + id + " non trovata");
        }
        strutturaRepository.deleteById(id);
    }

    private StrutturaResponseDto convertToResponseDto(Struttura struttura) {
        StrutturaResponseDto strutturaResponseDto = new StrutturaResponseDto();
        strutturaResponseDto.setId(struttura.getId());
        strutturaResponseDto.setNome(struttura.getNome());
        strutturaResponseDto.setDescrizione(struttura.getDescrizione());
        strutturaResponseDto.setCitta(struttura.getCitta());
        strutturaResponseDto.setIndirizzo(struttura.getIndirizzo());
        strutturaResponseDto.setPrezzo(struttura.getPrezzo());
        strutturaResponseDto.setDisponibile(struttura.getDisponibile());
        strutturaResponseDto.setMoodAssociato(struttura.getMoodAssociato());
        strutturaResponseDto.setCategoriaAlloggio(struttura.getCategoriaAlloggio());
        strutturaResponseDto.setImmaginiUrl(struttura.getImmaginiUrl());
        strutturaResponseDto.setServiziExtra(struttura.getServiziExtra()
                .stream()
                .map(ServizioExtra::getNome)
                .collect(Collectors.toList()));
        return strutturaResponseDto;
    }

    private Set<ServizioExtra> getServiziExtraByIds(List<Long> ids) {
        return ids.stream()
                .map(id -> servizioExtraRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Servizio extra con ID " + id + " non trovato")))
                .collect(Collectors.toSet());
    }

    public List<StrutturaResponseDto> findWithFiltersSimple(String citta, String mood, Double minPrezzo, Double maxPrezzo) {
        List<Struttura> strutture = strutturaRepository.findAll();

        return strutture.stream()
                .filter(s -> citta == null || s.getCitta().equalsIgnoreCase(citta))
                .filter(s -> mood == null || s.getMoodAssociato().equalsIgnoreCase(mood))
                .filter(s -> minPrezzo == null || s.getPrezzo() >= minPrezzo)
                .filter(s -> maxPrezzo == null || s.getPrezzo() <= maxPrezzo)
                .map(this::convertToResponseDto)
                .toList();
    }

}
