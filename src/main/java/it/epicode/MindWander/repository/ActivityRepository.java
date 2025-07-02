package it.epicode.MindWander.repository;

import it.epicode.MindWander.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByCittaIgnoreCase(String citta);
}
