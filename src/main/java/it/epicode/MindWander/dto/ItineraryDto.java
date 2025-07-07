package it.epicode.MindWander.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItineraryDto {
    @NotBlank(message = "Il titolo è obbligatorio")
    private String titoloIti;

    @Size(max = 500, message = "Descrizione troppo lunga")
    private String descrizioneIti;

    private boolean automatic;

    private List<ItineraryStepDto> steps = new ArrayList<>();

}
