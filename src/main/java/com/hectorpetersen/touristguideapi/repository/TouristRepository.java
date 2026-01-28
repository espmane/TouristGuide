package com.hectorpetersen.touristguideapi.repository;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TouristRepository {

    private final List<TouristAttraction> attractions = new ArrayList<>();
    private String UserAttraction;


    public void AttractionRepositoruy() {
        spitAttractions();
    }

    public void spitAttractions() {
        while (UserAttraction) {
            attractions.add(new TouristAttraction(UserAttraction, "Kære", UserAttraction, "Dette er hvad du skal gennemgå i dag "));
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
