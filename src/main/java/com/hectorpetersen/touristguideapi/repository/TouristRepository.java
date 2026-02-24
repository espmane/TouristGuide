package com.hectorpetersen.touristguideapi.repository;

import com.hectorpetersen.touristguideapi.model.Cities;
import com.hectorpetersen.touristguideapi.model.Tags;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

    private final List<TouristAttraction> attractions = new ArrayList<>();
    private final List<String> cities;
    private int nextId = 1;

    public TouristRepository() {
        populate();
        this.cities = getCities();
        for (TouristAttraction a : attractions) {
            System.out.println(a);
        }
    }

    public void save(TouristAttraction attraction) {
        attraction.setAttractionId(nextId++);
        attractions.add(attraction);
    }

    public void populate() {
        save(new TouristAttraction("EK", "Ehvervesakademi", "København", List.of(Tags.BØRNEVENLIG, Tags.SKOLE, Tags.GRATIS)));
        save(new TouristAttraction("Mash", "Spisested", "København", List.of(Tags.DYRT, Tags.OPLEVELSE)));
        save(new TouristAttraction("Tivoli", "Forlystelsespark", "København", List.of(Tags.UDENDØRS, Tags.OPLEVELSE, Tags.UNDERHOLDNING)));
    }

    public List<String> getCities() {
        List<String> listOfCities = new ArrayList<>();
        for (Cities city : Cities.values()) {
            listOfCities.add(city.getDisplayName());
        }
        return listOfCities;
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
            attraction.setTags(touristAttraction.getTags());
            attraction.setCity(touristAttraction.getCity());
        }
        return attraction;
    }
}