package it.epicode.MindWander.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    @JsonProperty("prezzoTotale")
    public double getPrezzoTotale() {
        long giorni = ChronoUnit.DAYS.between(dataInizio, dataFine);
        if (giorni <= 0) {
            return 0.0;
        }
        return giorni * struttura.getPrezzo();
    }
}
