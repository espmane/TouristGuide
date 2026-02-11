package com.hectorpetersen.touristguideapi.model;

public class TouristAttraction {
    private String name;
    private String description;
    private int attractionId;
    private String city;
    private Tags tag;

    public TouristAttraction(String name, String description, String city, Tags tag) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.tag = tag;
    }

    public Tags getTag() {
        return tag;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
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

    @Override
    public String toString() {
        return "TouristAttraction{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", attractionId=" + attractionId +
                ", city='" + city + '\'' +
                ", tag=" + tag +
                '}';
    }
}
