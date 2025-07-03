package it.epicode.MindWander.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServizioExtraDto {
    @NotBlank(message = "Il nome del servizio è obbligatorio")
    private String servizio;
}
