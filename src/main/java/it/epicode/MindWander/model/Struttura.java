package it.epicode.MindWander.model;

import it.epicode.MindWander.enumeration.CategoriaAlloggio;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Struttura {
    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String descrizione;
    private String citta;
    private String indirizzo;
    private Double prezzo;
    private Boolean disponibile = true;
    private String moodAssociato;
    @Enumerated(EnumType.STRING)
    private CategoriaAlloggio categoriaAlloggio;
    @ManyToMany
    private Set<ServizioExtra> serviziExtra = new HashSet<>();
    @ElementCollection
    private List<String> immaginiUrl = new ArrayList<>();
    @ManyToMany(mappedBy = "wishlist")
    private Set<User> utentiCheHannoInWishlist = new HashSet<>();

}
