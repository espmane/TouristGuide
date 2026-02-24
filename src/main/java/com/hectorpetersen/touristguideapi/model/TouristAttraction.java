package com.hectorpetersen.touristguideapi.model;

import java.util.ArrayList;
import java.util.List;

public class TouristAttraction {
    private String name;
    private String description;
    private int attractionId;
    private String city;
    private List<Tags> tags = new ArrayList<>();

    public TouristAttraction(String name, String description, String city, List<Tags> tags) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
    }

    public TouristAttraction() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TouristAttraction{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", attractionId=" + attractionId +
                ", city='" + city + '\'' +
                ", tags=" + tags +
                '}';
    }
}
