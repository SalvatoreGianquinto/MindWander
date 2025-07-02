package it.epicode.MindWander.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Activity {
    @Id
    @GeneratedValue
    private Long id;
    private String citta;
    private String nomeAct;
    private String descrizione;
    private String mood;
    private int oreConsigliate;
}
