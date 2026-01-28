package com.hectorpetersen.touristguideapi.repository;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TouristRepository {

    private final List<TouristAttraction> attractions = new ArrayList<>();
    private String attractionName;


    public TouristRepository() {
        theAttractions();
    }

    private void theAttractions() {
        attractions.add(new TouristAttraction(attractionName, "Tivoli: " + attractionName + "Her kan du prøve forlystelser og spise god mad "));
        attractions.add(new TouristAttraction(attractionName, "Tivoli: " + attractionName + "Her kan du prøve forlystelser og spise god mad "));
        attractions.add(new TouristAttraction(attractionName, "Tivoli: " + attractionName + "Her kan du prøve forlystelser og spise god mad "));
        attractions.remove(0);


        for (TouristAttraction a : attractions) {
            System.out.println(a);
        }
    }

    public List<TouristAttraction> getAllAttractions() {
        return attractions;


    }

    public TouristAttraction findAttractionsByName(String name) {
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName() == name) {
                return attraction;
            }
        }

        return null;
    }
}