package it.epicode.MindWander.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JoinColumn(name = "stanza_id", nullable = false)
    @JsonIgnoreProperties({"prenotazioni", "struttura"})
    private Stanza stanza;
    @JsonProperty("prezzoTotale")
    public double getPrezzoTotale() {
        if (stanza == null || stanza.getPrezzo() == null) {
            return 0.0;
        }
        long giorni = ChronoUnit.DAYS.between(dataInizio, dataFine);
        if (giorni <= 0) {
            return 0.0;
        }
        return giorni * stanza.getPrezzo();
    }
}
