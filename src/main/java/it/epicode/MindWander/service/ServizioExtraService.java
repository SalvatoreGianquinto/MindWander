package it.epicode.MindWander.service;

import it.epicode.MindWander.dto.ServizioExtraDto;
import it.epicode.MindWander.exception.NotFoundException;
import it.epicode.MindWander.model.ServizioExtra;
import it.epicode.MindWander.repository.ServizioExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ServizioExtraService {

    @Autowired
    private ServizioExtraRepository repository;

    public List<ServizioExtra> findAll() {
        return repository.findAll();
    }

    public ServizioExtra findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Servizio extra non trovato"));
    }

    public ServizioExtra create(ServizioExtraDto dto) {
        ServizioExtra extra = new ServizioExtra();
        extra.setServizio(dto.getServizio());
        return repository.save(extra);
    }

    public ServizioExtra update(Long id, ServizioExtraDto dto) {
        ServizioExtra extra = findById(id);
        extra.setServizio(dto.getServizio());
        return repository.save(extra);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Servizio extra non trovato");
        }
        repository.deleteById(id);
    }
}
