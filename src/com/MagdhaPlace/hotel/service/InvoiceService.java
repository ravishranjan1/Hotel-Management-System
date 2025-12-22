package com.MagdhaPlace.hotel.service;

import com.MagdhaPlace.hotel.model.InvoiceModel;

public interface InvoiceService {
    void generateInvoice(String bookingId, double totalCharge, double prePaid);
    InvoiceModel findByBookingId(String BookingId);
}
