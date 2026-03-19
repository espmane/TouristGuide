package com.hectorpetersen.touristguideapi.repository;

import com.hectorpetersen.touristguideapi.model.Tags;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

    private final List<TouristAttraction> attractions = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<Tags> getTagsForAttraction(int attractionId) {
        return jdbcTemplate.query("""
                        SELECT t.Name
                        FROM Tags t
                        JOIN Attraction_tags at ON t.Tags_ID = at.Tags_ID
                        WHERE at.Attractions_id = ?
                        """,
                (rs, rowNum) -> Tags.valueOf(rs.getString("Name").toUpperCase()), attractionId);
    }

    public List<TouristAttraction> getAllAttractions() {
        // Tags er ikke med
        final String attractionSQL = """
                SELECT a.Attractions_id AS ID,
                       a.Name AS Name,
                       a.Description AS Description,
                       c.Name AS City
                FROM Attractions a
                JOIN City c ON a.City_ID = c.City_ID;""";

        return jdbcTemplate.query(attractionSQL, (rs, rowNum) -> (new TouristAttraction(
                rs.getInt("ID"),
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getString("City"),
                getTagsForAttraction(rs.getInt("ID"))
        )));
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
        attraction.setAttractionId(1);
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