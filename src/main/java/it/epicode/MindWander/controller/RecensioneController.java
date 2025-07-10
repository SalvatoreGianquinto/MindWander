package it.epicode.MindWander.controller;

import it.epicode.MindWander.dto.RecensioneDto;
import it.epicode.MindWander.dto.RecensioneResponseDto;
import it.epicode.MindWander.model.User;
import it.epicode.MindWander.service.RecensioneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recensioni")
@RequiredArgsConstructor
public class RecensioneController {

    private final RecensioneService recensioneService;

    @PostMapping
    public RecensioneResponseDto creaRecensione(
            @RequestBody @Valid RecensioneDto recensioneDto,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        recensioneDto.setUserId(user.getId());
        return recensioneService.salvaRecensione(recensioneDto);
    }

    @GetMapping("/struttura/{strutturaId}")
    public List<RecensioneResponseDto> getRecensioni(@PathVariable Long strutturaId){
        return recensioneService.getRecensioniByStruttura(strutturaId);
    }

    @GetMapping("/mie")
    public List<RecensioneResponseDto> getRecensioniUtente(@AuthenticationPrincipal User user) {
        return recensioneService.getRecensioniByUser(user);
    }

    @GetMapping("/struttura/{strutturaId}/media")
    public Double getMedia(@PathVariable Long strutturaId) {
        return recensioneService.getMediaVotiByStruttura(strutturaId);
    }
}
