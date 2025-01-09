package learn.springframwork.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.springframwork.spring6restmvc.model.Customer;
import learn.springframwork.spring6restmvc.services.CustomerService;
import learn.springframwork.spring6restmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.accept;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    /* MockMVC helps you test the controller layer of a Spring-based web
    application without needing to run the application in a real web server(Tomcat etc)
    MockMVC allows us to simulate or "mock" the servlet context */
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setCustomerServiceImpl(){
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void getCustomerById() throws Exception {

        Customer customer = customerServiceImpl.getAllCustomers().get(0);

        // Stub Declared here(Function Call and Return Value);
        given(customerService.findCustomerById(customer.getId())).willReturn(customer);

        mockMvc.perform(get("/api/v1/customer/" + customer.getId(),accept(MediaType.APPLICATION_JSON)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(customer.getVersion()))
                .andExpect(jsonPath("$.customerName").value(customer.getCustomerName()));
    }


    @Test
    void testCreateCustomer() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().get(0);
        customer.setId(null);
        customer.setId(null);

        // Stub for Customer Service to be implemented for createCustomer
        given(customerService.addCustomer(any(Customer.class))).willReturn(customerServiceImpl.getAllCustomers().get(1));

        mockMvc.perform(post("/api/v1/customer")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer))
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }
}