package it.epicode.MindWander.repository;

import it.epicode.MindWander.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    @Query("SELECT p FROM Prenotazione p WHERE p.stanza.id = :stanzaId AND " +
            "(:dataInizio < p.dataFine AND :dataFine > p.dataInizio)")
    List<Prenotazione> findOverlappingPrenotazioni(
            @Param("stanzaId") Long stanzaId,
            @Param("dataInizio") LocalDate dataInizio,
            @Param("dataFine") LocalDate dataFine
    );

    List<Prenotazione> findByUserId(Long userId);

    List<Prenotazione> findByStanzaStrutturaId(Long strutturaId);
}
