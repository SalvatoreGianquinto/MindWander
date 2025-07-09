package it.epicode.MindWander.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RecensioneResponseDto {
    private Long id;
    private String autore;
    private int voto;
    private String commento;
    private LocalDate data = LocalDate.now();
}
