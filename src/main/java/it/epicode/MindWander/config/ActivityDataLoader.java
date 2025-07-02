package it.epicode.MindWander.config;

import it.epicode.MindWander.model.Activity;
import it.epicode.MindWander.repository.ActivityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Configuration
public class ActivityDataLoader {

    @Bean
    CommandLineRunner loadActivities(ActivityRepository activityRepository) {
        return args -> {
            if (activityRepository.count() > 0) {
                System.out.println("Activities already loaded. Skipping CSV import.");
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream("/data/activities.csv"), StandardCharsets.UTF_8))) {

                String line;
                reader.readLine(); // salta header
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length != 5) continue;

                    Activity activity = new Activity();
                    activity.setCitta(parts[0].trim());
                    activity.setNomeAct(parts[1].trim());
                    activity.setDescrizione(parts[2].trim());
                    activity.setMood(parts[3].trim());
                    activity.setOreConsigliate(Integer.parseInt(parts[4].trim()));

                    activityRepository.save(activity);
                }
                System.out.println("Activities loaded successfully from CSV!");
            } catch (Exception e) {
                System.err.println("Failed to load activities from CSV: " + e.getMessage());
            }
        };
    }
}