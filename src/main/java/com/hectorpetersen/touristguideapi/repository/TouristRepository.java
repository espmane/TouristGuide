package com.hectorpetersen.touristguideapi.repository;

import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TouristRepository {

    private final List<TouristAttraction> attractions = new ArrayList<>();


    public TouristRepository() {
        theAttractions();
    }

    private void theAttractions() {
        attractions.add(new TouristAttraction("EK", "Ehvervesakademi"));
        attractions.add(new TouristAttraction("Mash", "Spisested"));
        attractions.add(new TouristAttraction("Tivoli", "Forlystelsespark"));


        for (TouristAttraction a : attractions) {
            System.out.println(a);
        }
    }

    public List<TouristAttraction> getAllAttractions() {
        return attractions;
    }

    public TouristAttraction findAttractionsByName(String name) {
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                return attraction;
            }
        }

        return null;
    }

    public TouristAttraction createNewAttraction(String name, String description) {
        TouristAttraction attraction = new TouristAttraction(name, description);
        attractions.add(attraction);
        return attraction;
    }

    public void deleteAttraction(String name) {
        TouristAttraction attraction = findAttractionsByName(name);
        attractions.remove(attraction);
    }

    public void updateAttraction(String name, String description) {
        TouristAttraction attraction = findAttractionsByName(name);
        if (!description.equals(attraction.getDescription())) {
            attraction.setDescription(description);
        }
        //TODO kast en error kode tilbage hvis noget går galt
    }
}