package it.epicode.MindWander.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StanzaDto {
    private Long id;
    @NotBlank(message = "Il nome della stanza è obbligatorio")
    private String nome;
    @Min(value = 1, message = "La stanza deve avere almeno un posto letto")
    private int postiLetto;
    @NotBlank(message = "La descrizione della stanza è obbligatorio")
    private String descrizione;
    private Double prezzo;
    private Long strutturaId;
}
