package it.epicode.MindWander.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrenotazioneDto {
    @NotNull(message = "L'id della struttura è obbligatorio")
    private Long strutturaId;
    @NotNull(message = "La data di inizio è obbligatoria")
    @FutureOrPresent(message = "La data di inizio deve essere oggi o nel futuro")
    private LocalDate dataInizio;
    @NotNull(message = "La data di fine è obbligatoria")
    @FutureOrPresent(message = "La data di fine deve essere oggi o nel futuro")
    private LocalDate dataFine;
    @NotNull(message = "Numero ospiti è obbligatorio")
    private int numeroOspiti;
    private String note;
}
