package com.colivingspacemanager.app.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(collection = "colivingSpaces")
public class ColivingSpace {

    @Id
    private String id;
    private String spaceCode;
    private String spaceManagerCode;
    private String propertyName;
    private String address;
    private String ownerName;
    private String ownerContactNumber;
    private int totalRooms;
    private int totalSingleOccupancyRooms;
    private int availableSingleOccupancyRooms;
    private int totalDoubleOccupancyRooms;
    private int availableDoubleOccupancyRooms;
    private int totalTripleOccupancyRooms;
    private int availableTripleOccupancyRooms;
    private Occupancy occupancy;
    private double singleOccupancyRent;
    private double doubleOccupancyRent;
    private double TripleOccupancyRent;
    private double securityDeposit;
    private double overallRating;
    private SpaceStatus spaceStatus;
    private Set<Facilities> colivingFacilities;

}