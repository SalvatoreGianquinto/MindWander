package it.epicode.MindWander.controller;

import it.epicode.MindWander.dto.StrutturaDto;
import it.epicode.MindWander.dto.StrutturaResponseDto;
import it.epicode.MindWander.service.StrutturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/strutture")
@RequiredArgsConstructor
public class StrutturaController {
    private final StrutturaService strutturaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<StrutturaResponseDto> getAllStrutture() {
        return strutturaService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public StrutturaResponseDto getStrutturaById(@PathVariable Long id) {
        return strutturaService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StrutturaResponseDto createStruttura(@RequestBody @Valid StrutturaDto strutturaDto) {
        return strutturaService.save(strutturaDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public StrutturaResponseDto updateStruttura(@PathVariable Long id, @RequestBody @Valid StrutturaDto strutturaDto) {
        return strutturaService.update(id, strutturaDto);
    }

    @GetMapping("/filtrate")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<StrutturaResponseDto> getStruttureFiltrate(
            @RequestParam(required = false) String citta,
            @RequestParam(required = false) String mood,
            @RequestParam(required = false) Double minPrezzo,
            @RequestParam(required = false) Double maxPrezzo
    ) {
        return strutturaService.findWithFiltersSimple(citta, mood, minPrezzo, maxPrezzo);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStruttura(@PathVariable Long id) {
        strutturaService.delete(id);
    }
}
