package com.colivingspacemanager.app.entity;

public enum Facilities {
    WIFI("High-speed Wi-Fi"),
    FURNISHED_ROOMS("Furnished Rooms"),
    COMMON_KITCHEN("Common Kitchen"),
    LAUNDRY("Laundry Facilities"),
    CO_WORKING_SPACE("Co-working Space"),
    FITNESS_CENTER("Fitness Center"),
    COMMUNITY_EVENTS("Community Events"),
    SECURITY("Security Services"),
    HOUSEKEEPING("Housekeeping Services"),
    PARKING("Parking Facilities"),
    PET_FRIENDLY("Pet-friendly Environment"),
    ROOFTOP_TERRACE("Rooftop Terrace"),
    GAME_ROOM("Game Room"),
    CINEMA_ROOM("Cinema Room"),
    BIKE_STORAGE("Bike Storage"),
    SWIMMING_POOL("Swimming Pool"),
    BBQ_AREA("Barbecue Area"),
    GARDEN("Community Garden"),
    LOUNGE("Common Lounge"),
    ART_STUDIO("Art Studio"),
    MUSIC_ROOM("Music Room"),
    YOGA_STUDIO("Yoga Studio"),
    SMART_HOME_TECH("Smart Home Technology");

    private final String description;

    Facilities(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
