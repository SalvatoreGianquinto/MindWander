package it.epicode.MindWander.dto;

import it.epicode.MindWander.enumeration.CategoriaAlloggio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class StrutturaDto {
    @NotBlank(message = "Il nome è obbligatorio")
    private String nome;
    @NotBlank(message = "La descrizione è obbligatoria")
    private String descrizione;
    @NotBlank(message = "La città è obbligatoria")
    private String citta;
    @NotBlank(message = "L'indirizzo è obbligatorio")
    private String indirizzo;
    @NotNull(message = "Il prezzo è obbligatorio")
    @PositiveOrZero(message = "Il prezzo deve essere positivo o zero")
    private Double prezzo;
    @NotNull(message = "La disponibilità deve essere specificata")
    private Boolean disponibile;
    private String moodAssociato;
    @NotNull(message = "La categoria è obbligatoria")
    private CategoriaAlloggio categoriaAlloggio;
    @NotNull(message = "La lista dei servizi extra deve essere specificata")
    private List<Long> serviziExtraIds;
    @NotNull(message = "La lista delle immagini deve essere specificata")
    private List<String> immaginiUrl;
}
