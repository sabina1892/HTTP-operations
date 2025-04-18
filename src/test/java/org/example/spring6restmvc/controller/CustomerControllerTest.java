package org.example.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.spring6restmvc.model.Customer;
import org.example.spring6restmvc.service.CustomService;
import org.example.spring6restmvc.service.CustomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomService customService;

    CustomServiceImpl customServiceImpl;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        customServiceImpl = new CustomServiceImpl();
    }

    @Test
    void createCustomerTest() throws Exception{
        Customer customerTest = customServiceImpl.listCustomers().get(0);
        customerTest.setId(null);
        customerTest.setVersion(null);
        given(customService.savedCustomer(any(Customer.class))).willReturn(customServiceImpl.listCustomers().get(1));
        mockMvc.perform(post("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerTest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateCustomerTest() throws Exception{
        Customer customerTest = customServiceImpl.listCustomers().get(0);
        mockMvc.perform(put("/api/v1/customer/" + customerTest.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerTest)))
                .andExpect(status().isNoContent());
        verify(customService).updatedCustomerById(any(UUID.class),any(Customer.class));
    }

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
    @Test
    void deleteCustomerTest() throws Exception{
        Customer customer = customServiceImpl.listCustomers().get(0);
        mockMvc.perform(delete("/api/v1/customer/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(customService).deleteById(uuidArgumentCaptor.capture());
        assert(customer.getId()).equals(uuidArgumentCaptor.getValue());

    }

}