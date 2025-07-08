package it.epicode.MindWander.controller;

import it.epicode.MindWander.dto.GenerateItinaryDto;
import it.epicode.MindWander.dto.ItineraryDto;
import it.epicode.MindWander.dto.ItineraryStepDto;
import it.epicode.MindWander.model.Itinerary;
import it.epicode.MindWander.model.ItineraryStep;
import it.epicode.MindWander.model.User;
import it.epicode.MindWander.service.ItineraryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/itineraries")
public class ItineraryController {

    @Autowired
    private ItineraryService itineraryService;

    @PostMapping("/generate")
    public Itinerary generateItinerary(@AuthenticationPrincipal User authenticated,
                                       @Valid @RequestBody GenerateItinaryDto generateItinaryDto){
       return itineraryService.generateAutomaticItinerary(
               authenticated.getId(),
               generateItinaryDto.getCitta(),
               generateItinaryDto.getDays(),
               generateItinaryDto.getMood()
       );
    }

    @GetMapping
    public List<Itinerary> getUserItineraries(@AuthenticationPrincipal User authenticatedUser) {
        return itineraryService.getUserItineraries(authenticatedUser.getId());
    }

    @PostMapping("/create")
    public Itinerary createItinerary(@AuthenticationPrincipal User authenticatedUser,
                                     @Valid @RequestBody ItineraryDto itineraryDto) {
        Itinerary itinerary = new Itinerary();
        itinerary.setTitoloIti(itineraryDto.getTitoloIti());
        itinerary.setDescrizioneIti(itineraryDto.getDescrizioneIti());
        itinerary.setUserId(authenticatedUser.getId());
        itinerary.setAutomatic(itineraryDto.isAutomatic());;
        itinerary.setEditable(true);

        if (itineraryDto.getSteps() != null) {
            for (ItineraryStepDto stepDto : itineraryDto.getSteps()) {
                ItineraryStep step = new ItineraryStep();
                step.setLuogo(stepDto.getLuogo());
                step.setDescrActivity(stepDto.getDescrActivity());
                step.setGiornoPrevisto(LocalDate.now());
                step.setItinerary(itinerary);
                itinerary.getSteps().add(step);
            }
        }

        return itineraryService.saveItinerary(itinerary);
    }

    @PutMapping("/{id}")
    public Itinerary updateItinerary(@PathVariable Long id,
                                     @AuthenticationPrincipal User authenticatedUser,
                                     @Valid @RequestBody ItineraryDto itineraryDto) {

        Itinerary updated = new Itinerary();
        updated.setTitoloIti(itineraryDto.getTitoloIti());
        updated.setDescrizioneIti(itineraryDto.getDescrizioneIti());
        updated.setEditable(itineraryDto.isEditable());
        updated.setUserId(authenticatedUser.getId());

        if (itineraryDto.getSteps() != null) {
            for (ItineraryStepDto stepDto : itineraryDto.getSteps()) {
                ItineraryStep step = new ItineraryStep();
                step.setLuogo(stepDto.getLuogo());
                step.setDescrActivity(stepDto.getDescrActivity());
                step.setGiornoPrevisto(stepDto.getGiornoPrevisto());
                step.setItinerary(updated);
                updated.getSteps().add(step);
            }
        }

        return itineraryService.updateItinerary(id, authenticatedUser.getId(), updated);
    }


    @DeleteMapping("/{id}")
    public void deleteItinerary(@PathVariable Long id, @AuthenticationPrincipal User authenticatedUser) {
        itineraryService.deleteItinerary(id, authenticatedUser.getId());
    }

}
