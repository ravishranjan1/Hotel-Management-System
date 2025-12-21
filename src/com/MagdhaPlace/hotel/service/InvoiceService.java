package com.MagdhaPlace.hotel.service;

import com.MagdhaPlace.hotel.model.InvoiceModel;

public interface InvoiceService {
    void generateInvoice(InvoiceModel i);
    InvoiceModel findByBookingId(String BookingId);
}
