package it.epicode.MindWander.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PrenotazioneViewDto {
    private Long id;
    private String nomeStruttura;
    private String nomeStanza;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private int numeroOspiti;
    private double prezzoTotale;
    private String note;
}
