package it.epicode.MindWander.repository;

import it.epicode.MindWander.model.Stanza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StanzaRepository extends JpaRepository<Stanza, Long> {
    List<Stanza> findByStrutturaId(Long strutturaId);
}
