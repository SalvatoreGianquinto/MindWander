package it.epicode.MindWander.service;

import it.epicode.MindWander.dto.RecensioneDto;
import it.epicode.MindWander.dto.RecensioneResponseDto;
import it.epicode.MindWander.exception.NotFoundException;
import it.epicode.MindWander.model.Recensione;
import it.epicode.MindWander.model.Struttura;
import it.epicode.MindWander.model.User;
import it.epicode.MindWander.repository.RecensioneRepository;
import it.epicode.MindWander.repository.StrutturaRepository;
import it.epicode.MindWander.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecensioneService {

    private final RecensioneRepository recensioneRepository;
    private final UserRepository userRepository;
    private final StrutturaRepository strutturaRepository;

    public RecensioneResponseDto salvaRecensione(RecensioneDto recensioneDto){
        User user = userRepository.findById(recensioneDto.getUserId())
                .orElseThrow(() -> new NotFoundException("Utente con ID "+ recensioneDto.getUserId() + " non trovato"));

        Struttura struttura = strutturaRepository.findById(recensioneDto.getStrutturaId())
                .orElseThrow(() -> new NotFoundException("Struttura con ID " + recensioneDto.getStrutturaId() + " non trovata"));


        boolean recensioneEsistente = recensioneRepository
                .existsByUserIdAndStrutturaId(user.getId(), struttura.getId());

        if (recensioneEsistente) {
            throw new IllegalArgumentException("Hai già lasciato una recensione per questa struttura.");
        }


        Recensione recensione = new Recensione();
        recensione.setVoto(recensioneDto.getVoto());
        recensione.setCommento(recensioneDto.getCommento());
        recensione.setUser(user);
        recensione.setStruttura(struttura);
        recensione.setData(LocalDate.now());

        recensioneRepository.save(recensione);

        return new RecensioneResponseDto(
                user.getUsername(),
                recensione.getVoto(),
                recensione.getCommento(),
                recensione.getData()
        );
    }

    public List<RecensioneResponseDto> getRecensioniByStruttura(Long strutturaId){
        return recensioneRepository.findByStrutturaId(strutturaId).stream()
                .map(r -> new RecensioneResponseDto(
                        r.getUser().getUsername(),
                        r.getVoto(),
                        r.getCommento(),
                        r.getData()
                ))
                .toList();
    }

    public Double getMediaVotiByStruttura(Long strutturaId){
        Double media = recensioneRepository.calcolaMediaVotiPerStruttura(strutturaId);
        return media != null ? media : 0.0;
    }
}
