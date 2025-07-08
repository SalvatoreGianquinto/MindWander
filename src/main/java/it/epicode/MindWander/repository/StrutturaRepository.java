package it.epicode.MindWander.repository;

import it.epicode.MindWander.model.Struttura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StrutturaRepository extends JpaRepository<Struttura, Long> {
}
