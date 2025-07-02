package it.epicode.MindWander.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Itinerary {
    @Id
    @GeneratedValue
    private Long id;
    private String titoloIti;
    private String descrizioneIti;
    private boolean automatic;
    private boolean editable;
    private Long userId;
    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItineraryStep> steps = new ArrayList<>();
}
