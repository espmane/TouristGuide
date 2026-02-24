package com.hectorpetersen.touristguideapi.model;

public enum Cities {
    KØBENHAVN,
    AARHUS,
    ODENSE,
    AALBORG,
    ESBJERG,
    RANDERS,
    KOLDING,
    HORSENS,
    ROSKILDE,
    SILKEBORG,
    NÆSTVED,
    HILLERØD,
    HELSINGØR,
    FREDERICIA,
    HORNBÆK;

    public String getDisplayName() {
        String name = this.name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}
