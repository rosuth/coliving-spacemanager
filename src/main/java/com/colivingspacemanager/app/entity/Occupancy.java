package com.colivingspacemanager.app.entity;

public enum Occupancy {
    SINGLE("Single Occupancy"),
    DOUBLE("Double Occupancy"),
    TRIPLE("Triple Occupancy"),
    ALL("All Occupancies");

    private final String description;

    Occupancy(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
