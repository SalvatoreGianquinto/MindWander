package it.epicode.MindWander.controller;

import it.epicode.MindWander.dto.RecensioneDto;
import it.epicode.MindWander.dto.RecensioneResponseDto;
import it.epicode.MindWander.service.RecensioneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recensioni")
@RequiredArgsConstructor
public class RecensioneController {

    private final RecensioneService recensioneService;

    @PostMapping
    public RecensioneResponseDto creaRecensione(@RequestBody @Valid RecensioneDto recensioneDto){
        return recensioneService.salvaRecensione(recensioneDto);
    }

    @GetMapping("/struttura/{strutturaId}")
    public List<RecensioneResponseDto> getRecensioni(@PathVariable Long strutturaId){
        return recensioneService.getRecensioniByStruttura(strutturaId);
    }

    @GetMapping("/struttura/{strutturaId}/media")
    public Double getMedia(@PathVariable Long strutturaId) {
        return recensioneService.getMediaVotiByStruttura(strutturaId);
    }
}
