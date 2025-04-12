package org.example.spring6restmvc.controller;

import org.example.spring6restmvc.model.Customer;
import org.example.spring6restmvc.service.CustomService;
import org.example.spring6restmvc.service.CustomServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomService customService;

    CustomServiceImpl customServiceImpl = new CustomServiceImpl();

    @Test
    void testListCustomer() throws Exception {
        given(customService.listCustomers()).willReturn(customServiceImpl.listCustomers());
        mockMvc.perform(get("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(2)));
    }
    @Test
    void getCustomerByID() throws Exception{
        Customer customer = customServiceImpl.listCustomers().get(0);
        given(customService.getCustomerById(customer.getId())).willReturn(customer);
        mockMvc.perform(get("/api/v1/customer/"+customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(customer.getId().toString())));
    }
}