package com.MagdhaPlace.hotel.model;

public class InvoiceModel {
    private String id;
    private String bookingId;
    private double roomCharges;
    private double taxes;
    private double additionalCharges;
    private double totalDue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public double getRoomCharges() {
        return roomCharges;
    }

    public void setRoomCharges(double roomCharges) {
        this.roomCharges = roomCharges;
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    public double getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(double additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(double totalDue) {
        this.totalDue = totalDue;
    }
    @Override
    public String toString() {
        return "InvoiceModel{" +
                "id='" + id + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", roomCharges=" + roomCharges +
                ", taxes=" + taxes +
                ", additionalCharges=" + additionalCharges +
                ", totalDue=" + totalDue +
                '}';
    }
}
