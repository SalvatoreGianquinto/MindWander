package it.epicode.MindWander.controller;

import it.epicode.MindWander.dto.PrenotazioneDto;
import it.epicode.MindWander.dto.PrenotazioneViewDto;
import it.epicode.MindWander.model.Prenotazione;
import it.epicode.MindWander.model.User;
import it.epicode.MindWander.service.PrenotazioneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioniController {

    private final PrenotazioneService prenotazioneService;

    @PostMapping
    public Prenotazione creaPrenotazione(
            @RequestBody @Valid PrenotazioneDto prenotazioneDto,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        return prenotazioneService.creaPrenotazione(prenotazioneDto, user);
    }

    @PutMapping("/{id}")
    public Prenotazione modificaPrenotazione(
            @PathVariable Long id,
            @RequestBody @Valid PrenotazioneDto prenotazioneDto,
            @AuthenticationPrincipal User user) {
        return prenotazioneService.modificaPrenotazione(id, prenotazioneDto, user);
    }

    @DeleteMapping("/{id}")
    public void cancellaPrenotazione(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        prenotazioneService.cancellaPrenotazione(id, user);
    }

    @GetMapping("/miei")
    public List<PrenotazioneViewDto> getPrenotazioniUtente(@AuthenticationPrincipal User user) {
        return prenotazioneService.trovaPrenotazioniUtenteView(user);
    }

}
