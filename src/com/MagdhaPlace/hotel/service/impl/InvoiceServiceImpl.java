package com.MagdhaPlace.hotel.service.impl;

import com.MagdhaPlace.hotel.model.InvoiceModel;
import com.MagdhaPlace.hotel.repo.InvoiceRepo;
import com.MagdhaPlace.hotel.service.InvoiceService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepo invoiceRepo;
    private final Logger log = Logger.getLogger(InvoiceServiceImpl.class.getName());

    public InvoiceServiceImpl(InvoiceRepo invoiceRepo) {
        this.invoiceRepo = invoiceRepo;
    }

    @Override
    public void generateInvoice(String bookingId, double totalCharge, double prePaid) {
        try {
            InvoiceModel i = new InvoiceModel();
            String id = String.valueOf(UUID.randomUUID());
            i.setId(id);
            i.setBookingId(bookingId);
            i.setRoomCharges(totalCharge);
            double tax = totalCharge*0.18;
            i.setTaxes(tax);
            double due = (totalCharge+tax)-prePaid;
            i.setTotalDue(due);
            i.setAdditionalCharges(0.0);
            invoiceRepo.gererateInvoice(i);
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }


    }

    @Override
    public InvoiceModel findByBookingId(String bookingId) {
        try {
            List<InvoiceModel> list = invoiceRepo.findAll();
            for(InvoiceModel i : list){
                if(i.getBookingId().equalsIgnoreCase(bookingId)){
                    return i;
                }
            }
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
        return null;
    }
}
