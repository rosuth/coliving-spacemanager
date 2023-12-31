package com.colivingspacemanager.app.entity;

public enum SpaceStatus {

    ACTIVE("The coliving space is currently operational and available for tenants"),
    CLOSED("The coliving space is temporarily or permanently closed and not available for tenants"),
    PENDING_APPROVAL("The coliving space is awaiting approval or verification before becoming active"),
    SUSPENDED("The coliving space is temporarily suspended, perhaps due to a violation of terms or other issues"),
    INACTIVE("The coliving space is not currently in use, but it may become active in the future"),
    FULL_CAPACITY("The coliving space has reached its maximum occupancy, and no more tenants can be accommodated"),
    NOT_APPROVED("The coliving space couldn't be approved");

    private final String description;

    SpaceStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
