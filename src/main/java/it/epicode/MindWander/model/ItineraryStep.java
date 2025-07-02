package it.epicode.MindWander.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class ItineraryStep {
    @Id
    @GeneratedValue
    private Long id;
    private String luogo;
    private String descrActivity;
    private LocalDate giornoPrevisto;

    @ManyToOne
    @JoinColumn(name = "itinerary_id")
    @JsonIgnore
    private Itinerary itinerary;
}
