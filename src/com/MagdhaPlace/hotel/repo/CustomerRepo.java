package com.MagdhaPlace.hotel.repo;

import com.MagdhaPlace.hotel.exceptions.CustomerAlreadyPresentException;
import com.MagdhaPlace.hotel.model.CustomerModel;

import java.io.IOException;
import java.util.List;

public interface CustomerRepo {
    void addCustomer(CustomerModel c) throws IOException;
    List<CustomerModel> findAll() throws IOException;
}
