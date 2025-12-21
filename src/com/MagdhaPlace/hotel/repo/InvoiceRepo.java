package com.MagdhaPlace.hotel.repo;

import com.MagdhaPlace.hotel.model.BookingModel;
import com.MagdhaPlace.hotel.model.InvoiceModel;

import java.io.IOException;
import java.util.List;

public interface InvoiceRepo {
    void gererateInvoice(InvoiceModel i)throws IOException;
    List<InvoiceModel> findAll() throws IOException;
}
