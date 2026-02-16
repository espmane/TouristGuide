package com.hectorpetersen.touristguideapi.model;

public enum Tags {
    BØRNEVENLIG, GRATIS, KUNST, MUSEUM, NATUR, FORLYSTELSESPARK;

    public String getDisplayName() {
        String name = this.name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}
