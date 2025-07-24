package it.epicode.MindWander.controller;

import it.epicode.MindWander.dto.StanzaDto;
import it.epicode.MindWander.model.Stanza;
import it.epicode.MindWander.service.StanzaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stanze")
@RequiredArgsConstructor
public class StanzaController {

    private final StanzaService stanzaService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Stanza getStanzaById(@PathVariable Long id) {
        return stanzaService.getStanzaById(id);
    }

    @GetMapping("/struttura/{strutturaId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Stanza> getStanzeByStruttura(@PathVariable Long strutturaId) {
        return stanzaService.getStanzeByStruttura(strutturaId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StanzaDto creaStanza(@Valid @RequestBody StanzaDto stanzaDto) {
        return stanzaService.creaStanza(stanzaDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public StanzaDto aggiornaStanza(@PathVariable Long id, @Valid @RequestBody StanzaDto stanzaDto) {
        return stanzaService.aggiornaStanza(id, stanzaDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminaStanza(@PathVariable Long id) {
        stanzaService.eliminaStanza(id);
    }
}
