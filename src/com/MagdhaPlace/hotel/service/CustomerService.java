package com.MagdhaPlace.hotel.service;

import com.MagdhaPlace.hotel.model.CustomerModel;

public interface CustomerService {
    void addCustomer(CustomerModel c);
    CustomerModel findById(String id);

}
