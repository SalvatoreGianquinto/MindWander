package it.epicode.MindWander.service;

import it.epicode.MindWander.exception.NotFoundException;
import it.epicode.MindWander.model.Activity;
import it.epicode.MindWander.model.Itinerary;
import it.epicode.MindWander.model.ItineraryStep;
import it.epicode.MindWander.repository.ActivityRepository;
import it.epicode.MindWander.repository.ItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItineraryService {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    public Itinerary generateAutomaticItinerary(Long userId, String city, int days, String mood) {
        List<Activity> attivita = activityRepository.findByCittaIgnoreCase(city);

        if (attivita.isEmpty()) {
            throw new NotFoundException("Nessuna attività trovata per la città: " + city);
        }

        List<Activity> attivitaFiltrate = attivita.stream()
                .filter(a -> mood == null || a.getMood() != null && a.getMood().equalsIgnoreCase(mood))
                .toList();

        if (attivitaFiltrate.isEmpty()) {
            throw new NotFoundException("Nessuna attività trovata con il mood: " + mood + " nella città: " + city);
        }

        int oreTotali = days * 12;
        List<ItineraryStep> steps = new ArrayList<>();
        int hoursCount = 0;
        int day = 1;

        for (Activity activity : attivitaFiltrate) {
            if (hoursCount + activity.getOreConsigliate() > oreTotali) break;

            ItineraryStep step = new ItineraryStep();
            step.setLuogo(activity.getNomeAct());
            step.setDescrActivity(activity.getDescrizione());
            step.setGiornoPrevisto(LocalDate.now().plusDays(day - 1));

            steps.add(step);
            hoursCount += activity.getOreConsigliate();

            if (hoursCount % 12 == 0) day++;
        }

        Itinerary itinerary = new Itinerary();
        itinerary.setTitoloIti("Itinerario automatico per " + city);
        itinerary.setDescrizioneIti("Generato in base a durata e mood");
        itinerary.setAutomatic(true);
        itinerary.setEditable(true);
        itinerary.setUserId(userId);

        steps.forEach(step -> step.setItinerary(itinerary));
        itinerary.setSteps(steps);

        return itineraryRepository.save(itinerary);
    }

    public List<Itinerary> getUserItineraries(Long userId) {
        return itineraryRepository.findByUserId(userId);
    }

    public Itinerary saveItinerary(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }
}
