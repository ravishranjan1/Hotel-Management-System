package com.MagdhaPlace.hotel.service.impl;

import com.MagdhaPlace.hotel.exceptions.CustomerAlreadyPresentException;
import com.MagdhaPlace.hotel.model.CustomerModel;
import com.MagdhaPlace.hotel.repo.CustomerRepo;
import com.MagdhaPlace.hotel.service.CustomerService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private static final Logger log = Logger.getLogger(CustomerServiceImpl.class.getName());

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public void addCustomer(CustomerModel c) {
        try {
            customerRepo.addCustomer(c);
            log.info("Register Customer successfully");
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
    }

    @Override
    public CustomerModel findById(String id) {
        try {
            List<CustomerModel> list = customerRepo.findAll();
            for(CustomerModel x : list){
                if(x.getId().equalsIgnoreCase(id)){
                    log.info("Customer found with Id : "+id);
                    return x;
                }
            }
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
        return null;
    }
}
