package it.epicode.MindWander.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "Il username è obbligatorio")
    @Size(min = 3, max = 20, message = "Il username deve contenere tra 3 e 20 caratteri")
    private String username;
    @NotBlank(message = "La password è obbligatoria")
    @Size(min = 6, max = 30, message = "La password deve contenere almeno 6 caratteri")
    private String password;
}

