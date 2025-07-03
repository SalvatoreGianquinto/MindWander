package it.epicode.MindWander.controller;

import it.epicode.MindWander.dto.ServizioExtraDto;
import it.epicode.MindWander.model.ServizioExtra;
import it.epicode.MindWander.service.ServizioExtraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servizi-extra")
public class ServizioExtraController {

    @Autowired
    private ServizioExtraService servizioExtraService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<ServizioExtra> getAll() {
        return servizioExtraService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ServizioExtra getById(@PathVariable Long id) {
        return servizioExtraService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ServizioExtra create(@RequestBody @Valid ServizioExtraDto servizioExtraDto) {
        return servizioExtraService.create(servizioExtraDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ServizioExtra update(@PathVariable Long id, @RequestBody @Valid ServizioExtraDto dto) {
        return servizioExtraService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        servizioExtraService.delete(id);
    }
}
