package it.epicode.MindWander.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Recensione {
    @Id
    @GeneratedValue
    private Long id;
    private int voto;
    private String commento;
    private LocalDate data;
    @ManyToOne
    private User user;
    @ManyToOne
    private Struttura struttura;
}
