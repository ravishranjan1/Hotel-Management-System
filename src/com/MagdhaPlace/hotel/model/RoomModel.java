package com.MagdhaPlace.hotel.model;

import java.util.Set;

public class RoomModel {
    private String id;
    private String type;
    private int beds;
    private double nightlyRate;
    private Set<String> amenities;
    private RoomStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public double getNightlyRate() {
        return nightlyRate;
    }

    public void setNightlyRate(double nightlyRate) {
        this.nightlyRate = nightlyRate;
    }

    public Set<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<String> amenities) {
        this.amenities = amenities;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RoomModel{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", beds=" + beds +
                ", nightlyRate=" + nightlyRate +
                ", amenities=" + amenities +
                ", status=" + status +
                '}';
    }
}
