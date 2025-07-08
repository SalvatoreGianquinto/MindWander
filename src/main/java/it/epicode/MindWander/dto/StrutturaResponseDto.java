package it.epicode.MindWander.dto;

import it.epicode.MindWander.enumeration.CategoriaAlloggio;
import lombok.Data;

import java.util.List;

@Data
public class StrutturaResponseDto {
    private Long id;
    private String nome;
    private String descrizione;
    private String citta;
    private String indirizzo;
    private Double prezzo;
    private Boolean disponibile;
    private String moodAssociato;
    private CategoriaAlloggio categoriaAlloggio;
    private List<String> serviziExtra;
    private List<String> immaginiUrl;
}
