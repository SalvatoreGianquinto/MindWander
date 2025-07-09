package it.epicode.MindWander.repository;

import it.epicode.MindWander.model.Recensione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecensioneRepository extends JpaRepository<Recensione, Long > {
    List<Recensione> findByStrutturaId(Long strutturaId);

    @Query("SELECT AVG(r.voto) FROM Recensione r WHERE r.struttura.id = :strutturaId")
    Double calcolaMediaVotiPerStruttura(@Param("strutturaId") Long strutturaId);

    boolean existsByUserIdAndStrutturaId(Long userId, Long strutturaId);
}
