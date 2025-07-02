package it.epicode.MindWander.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenerateItinaryDto {
    @NotBlank(message = "Il parametro città non può essere vuoto")
    private String citta;
    @Min(value = 1, message = "Il numero di giorni deve essere maggiore di zero")
    private int days;
    private String mood;
}
