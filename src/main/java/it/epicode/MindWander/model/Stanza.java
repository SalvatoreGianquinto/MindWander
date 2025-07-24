package it.epicode.MindWander.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Stanza {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private int postiLetto;
    private String descrizione;
    private Double prezzo;
    @ManyToOne
    @JoinColumn(name = "struttura_id")
    private Struttura struttura;
    @OneToMany(mappedBy = "stanza")
    private List<Prenotazione> prenotazioni;
}
