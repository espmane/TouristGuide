package com.hectorpetersen.touristguideapi.repository;

import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TouristRepository {

    private final List<TouristAttraction> attractions = new ArrayList<>();

    public TouristRepository() {
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
            if (name.equalsIgnoreCase(attraction.getName())) {
                return attraction;
            }
        }
        return null;
    }

    public TouristAttraction createNewAttraction(TouristAttraction attraction) {
        if (findAttractionsByName(attraction.getName()) != null) {
            return null;
        }
        attractions.add(attraction);
        return attraction;
    }

    public TouristAttraction deleteAttraction(String name) {
        TouristAttraction deleteAttraction = findAttractionsByName(name);
        attractions.remove(deleteAttraction);
        return deleteAttraction;
    }

    public TouristAttraction updateAttraction(TouristAttraction touristAttraction) {
        TouristAttraction attraction = findAttractionsByName(touristAttraction.getName());
        if (attraction != null) {
            attraction.setDescription(touristAttraction.getDescription());
        }
        return attraction;
    }
}