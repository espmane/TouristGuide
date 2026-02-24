package com.hectorpetersen.touristguideapi.model;

public enum Tags {
    GRATIS,
    BILLIG,
    DYRT,
    INDENDØRS,
    UDENDØRS,
    SKOLE,
    BØRNEVENLIG,
    MUSEUM,
    KUNST,
    OPLEVELSE,
    AKTIVITET,
    UNDERHOLDNING,
    DYRELIV,
    HISTORIE,
    VIDENSKAB;

    public String getDisplayName() {
        String name = this.name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}
