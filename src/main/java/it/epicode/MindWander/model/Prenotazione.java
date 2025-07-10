package it.epicode.MindWander.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private LocalDate dataInizio;
    @Column(nullable = false)
    private LocalDate dataFine;
    @Column(name = "numero_ospiti", nullable = false)
    private int numeroOspiti;
    @Column(name = "note")
    private String note;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "struttura_id", nullable = false)
    private Struttura struttura;
}
