package com.hectorpetersen.touristguideapi.repository;

import com.hectorpetersen.touristguideapi.model.Tags;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

    private final List<TouristAttraction> attractions = new ArrayList<>();
    private int nextId = 1;

    public TouristRepository() {
        populate();
        for (TouristAttraction a : attractions) {
            System.out.println(a);
        }
    }

    public TouristAttraction save(TouristAttraction attraction) {
        attraction.setAttractionId(nextId++);
        attractions.add(attraction);
        return attraction;
    }

    public void populate() {
        save(new TouristAttraction("EK", "Ehvervesakademi", "København", Tags.BØRNEVENLIG));
        save(new TouristAttraction("Mash", "Spisested", "København", Tags.KUNST));
        save(new TouristAttraction("Tivoli", "Forlystelsespark", "København", Tags.FORLYSTELSESPARK));
    }

//    public List<String> getCities() {
//        // TODO: liste af byer
//
//    }

//    public List<String> getTags() {
//        // TODO: liste af tags
//    }

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
        attraction.setAttractionId(nextId++);
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
            attraction.setTag(touristAttraction.getTag());
            attraction.setCity(touristAttraction.getCity());
        }
        return attraction;
    }
}