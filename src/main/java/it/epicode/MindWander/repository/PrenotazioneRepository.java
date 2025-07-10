package it.epicode.MindWander.repository;

import it.epicode.MindWander.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByStrutturaIdAndDataFineAfterAndDataInizioBefore(
            Long strutturaId, LocalDate dataInizio, LocalDate dataFine);

    List<Prenotazione> findByUserId(Long userId);
}
