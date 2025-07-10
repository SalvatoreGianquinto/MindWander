package it.epicode.MindWander.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RecensioneDto {
    @Min(value = 1, message = "Il voto minimo è 1")
    @Max(value = 10,  message = "Il voto massimo è 10")
    private int voto;
    @NotBlank(message = "Il commento non può essere vuoto")
    private String commento;
    private Long userId;
    @NotNull(message = "L'ID della struttura è obbligatorio")
    private Long strutturaId;
}
