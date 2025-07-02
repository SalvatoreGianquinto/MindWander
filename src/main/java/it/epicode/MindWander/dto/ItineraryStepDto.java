package it.epicode.MindWander.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ItineraryStepDto {
    @NotBlank(message = "Il luogo è obbligatorio")
    private String luogo;
    private String descrActivity;
    private LocalDate giornoPrevisto;
}
