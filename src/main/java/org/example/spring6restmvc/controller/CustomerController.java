package org.example.spring6restmvc.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.spring6restmvc.model.Customer;
import org.example.spring6restmvc.service.CustomService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomService customService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        return customService.listCustomers();
    }

    @RequestMapping(value = "{customerId}",method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID getCustomerID){
        return customService.getCustomerById(getCustomerID);
    }
    @PostMapping
    public ResponseEntity saveCustomer(@RequestBody Customer customer){
         Customer saveCustomer = customService.savedCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + saveCustomer.getId());
        return new ResponseEntity(HttpStatus.CREATED) ;
    }
    @PutMapping("{customerId}")
    public ResponseEntity updateCustomer(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer){
        customService.updatedCustomerById(customerId, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("{customerId}")
    public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId){
        customService.deleteById(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}