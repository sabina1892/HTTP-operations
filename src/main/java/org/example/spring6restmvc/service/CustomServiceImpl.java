package org.example.spring6restmvc.service;

import org.example.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
        public class CustomServiceImpl implements CustomService {
            Map<UUID, Customer> customers;

            public CustomServiceImpl() {
                this.customers = new HashMap<>();
                Customer customer1 = Customer.builder()
                        .customerName("alice")
                        .version(112)
                        .id(UUID.randomUUID())
                        .lastModifiedDate(LocalDateTime.now())
                        .createdDate(LocalDateTime.now()).build();
                Customer customer2 = Customer.builder()
                        .customerName("joy")
                        .version(113)
                        .id(UUID.randomUUID())
                        .lastModifiedDate(LocalDateTime.now())
                        .createdDate(LocalDateTime.now()).build();
                customers.put(customer1.getId(), customer1);
                customers.put(customer2.getId(), customer2);
            }

            @Override
            public List<Customer> listCustomers() {
                return new ArrayList<>(customers.values());
            }

            @Override
            public Customer getCustomerById(UUID id) {
                return customers.get(id);
            }

            @Override
            public Customer savedCustomer(Customer customer) {
                Customer saveCustomer = customer.builder()
                        .customerName("Mike")
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .id(UUID.randomUUID())
                        .version(114)
                        .build();
                customers.put(saveCustomer.getId(),saveCustomer);
        return saveCustomer;
    }

    @Override
    public void updatedCustomerById(UUID customerId, Customer customer) {
        Customer existing = customers.get(customerId);
        existing.setCustomerName(customer.getCustomerName());
        existing.setId(customer.getId());
        existing.setVersion(customer.getVersion());
        existing.setCreatedDate(LocalDateTime.now());
        existing.setLastModifiedDate(LocalDateTime.now());
        customers.put(existing.getId(),existing);
    }

    @Override
    public void deleteById(UUID customerId) {
        customers.remove(customerId);
    }
}
