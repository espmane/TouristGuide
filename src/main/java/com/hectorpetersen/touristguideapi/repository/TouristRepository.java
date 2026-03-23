package com.hectorpetersen.touristguideapi.repository;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
        final String sql = """
                SELECT t.Name
                        FROM Tags t
                        JOIN Attraction_tags at ON t.Tags_ID = at.Tags_ID
                        WHERE at.Attractions_id = ?
                """;
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Tags.valueOf(rs.getString("Name").toUpperCase()), attractionId);
    }

    public List<TouristAttraction> getAllAttractions() {
        final String sql = """
                SELECT a.Attractions_id AS ID,
                       a.Name,
                       a.Description,
                       c.Name AS City
                FROM Attractions a
                    JOIN City c ON a.City_ID = c.City_ID;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> (new TouristAttraction(
                rs.getInt("ID"),
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getString("City"),
                getTagsForAttraction(rs.getInt("ID"))
        )));
    }

    public TouristAttraction findAttractionsByName(String name) {
        final String sql = """
                SELECT a.Attractions_id AS ID,
                       a.Name           AS Name,
                       a.Description    AS Description,
                       c.Name           AS CityName
                FROM Attractions a
                         JOIN City c ON a.City_ID = c.City_ID
                WHERE a.Name = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new TouristAttraction(
                rs.getInt("ID"),
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getString("CityName"),
                getTagsForAttraction(rs.getInt("ID"))), name
        );
    }



    public TouristAttraction createNewAttraction(TouristAttraction attraction) {
        try {
            TouristAttraction existing = findAttractionsByName(attraction.getName());
            if (existing != null) {
                return null;
            }
        } catch (Exception ignored) {

        }

        final String insertSql = """
            
                INSERT INTO Attractions (Name, Description, City_ID)
                            VALUES (?, ?, (SELECT City_ID FROM City WHERE Name = ?))
            """;

        jdbcTemplate.update(insertSql,
                attraction.getName(),
                attraction.getDescription(),
                attraction.getCity()
        );

        final String idSql = "SELECT Attractions_ID FROM Attractions WHERE Name = ?";
        Integer attractionId = jdbcTemplate.queryForObject(idSql, Integer.class, attraction.getName());

        final String tagSql = """
    INSERT INTO Attraction_Tags (Attractions_ID, Tags_ID)
    VALUES (?, (SELECT Tags_ID FROM Tags WHERE Name = ?))
""";

        for (Tags tag : attraction.getTags()) {
            jdbcTemplate.update(tagSql, attractionId, tag.name());
        }

        return new TouristAttraction(
                attractionId,
                attraction.getName(),
                attraction.getDescription(),
                attraction.getCity(),
                attraction.getTags()
        );
    }

    public TouristAttraction deleteAttraction(String name) {
        TouristAttraction deleteAttraction = findAttractionsByName(name);

        if (deleteAttraction == null){
            return null;
        }

        jdbcTemplate.update(
                "DELETE FROM Attraction_tags WHERE Attractions_id = ?", deleteAttraction.getAttractionId()
        );

        jdbcTemplate.update(
                "DELETE FROM Attractions WHERE Attractions_id = ?", deleteAttraction.getAttractionId()
        );

        return deleteAttraction;
    }

    public TouristAttraction updateAttraction(TouristAttraction touristAttraction) {
        TouristAttraction existingAttraction = findAttractionsByName(touristAttraction.getName());

        if (existingAttraction == null) {
            return null;
        }

        String updateSql = """
        UPDATE Attractions
        SET Description = ?, 
            City_ID = (SELECT City_ID FROM City WHERE Name = ?)
        WHERE Name = ?
        """;

        int rows = jdbcTemplate.update(
                updateSql,
                touristAttraction.getDescription(),
                touristAttraction.getCity(),
                touristAttraction.getName()
        );

        if (rows == 0) {
            return null;
        }

        jdbcTemplate.update(
                "DELETE FROM Attraction_tags WHERE Attractions_id = ?",
                existingAttraction.getAttractionId()
        );

        if (touristAttraction.getTags() != null) {
            String insertTagSql = """
            INSERT INTO Attraction_tags (Attractions_id, Tags_ID)
            VALUES (?, (SELECT Tags_ID FROM Tags WHERE Name = ?))
            """;

            for (Tags tag : touristAttraction.getTags()) {
                jdbcTemplate.update(
                        insertTagSql,
                        existingAttraction.getAttractionId(),
                        tag.name()
                );
            }
        }

        return findAttractionsByName(touristAttraction.getName());
    }

    }