package com.MagdhaPlace.hotel.handler;

import com.MagdhaPlace.hotel.exceptions.CustomerAlreadyPresentException;
import com.MagdhaPlace.hotel.model.CustomerModel;
import com.MagdhaPlace.hotel.service.CustomerService;

import java.util.Scanner;
import java.util.logging.Logger;

public class HandleCustomer {
    private final CustomerService customerService;
    private static final Logger log = Logger.getLogger(HandleCustomer.class.getName());

    public HandleCustomer(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void handleAddCustomer(Scanner sc){
        System.out.print("Enter Customer id : ");
        String id = sc.nextLine().trim();

        if(customerService.findById(id) == null){
            System.out.print("Enter name : ");
            String name = sc.nextLine().trim();

            System.out.print("Enter phone : ");
            String phone = sc.nextLine().trim();

            System.out.print("Enter email : ");
            String email = sc.nextLine().trim();

            CustomerModel c = new CustomerModel();
            c.setId(id);
            c.setName(name);
            c.setPhone(phone);
            c.setEmail(email);

            customerService.addCustomer(c);
            System.out.println("Customer added successfully");
        }else{
            try {
                throw new CustomerAlreadyPresentException("Customer with this id : "+id+" Already present");
            } catch (CustomerAlreadyPresentException e) {
                log.severe("CustomerAlreadyPresentException occurred, "+e.getMessage());
            }
            System.out.println("Customer Not added");
        }
    }


}
