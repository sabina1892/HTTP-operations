package org.example.spring6restmvc.service;

import org.example.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomService {
    List<Customer> listCustomers();

    Customer getCustomerById(UUID id);

    Customer savedCustomer(Customer customer);

    void updatedCustomerById(UUID customerId, Customer customer);

    void deleteById(UUID customerId);
}
