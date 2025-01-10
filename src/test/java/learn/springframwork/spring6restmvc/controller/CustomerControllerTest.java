package learn.springframwork.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.springframwork.spring6restmvc.model.Customer;
import learn.springframwork.spring6restmvc.services.CustomerService;
import learn.springframwork.spring6restmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Captor
    ArgumentCaptor<Customer> customerCaptor;

    @Captor
    ArgumentCaptor<UUID> uuidCustomerCaptor;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setCustomerServiceImpl(){
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void getCustomerById() throws Exception {

        Customer customer = customerServiceImpl.getAllCustomers().get(0);

        // Stub Declared here(Function Call and Return Value);
        given(customerService.findCustomerById(customer.getId())).willReturn(Optional.of(customer));

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID,customer.getId(),accept(MediaType.APPLICATION_JSON)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(customer.getVersion()))
                .andExpect(jsonPath("$.customerName").value(customer.getCustomerName()));
    }


    @Test
    void testCreateCustomer() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().get(0);
        customer.setId(null);
        customer.setCustomerName(null);

        // Stub for Customer Service to be implemented for createCustomer
        given(customerService.addCustomer(any(Customer.class))).willReturn(customerServiceImpl.getAllCustomers().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer))
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer testCustomer = customerServiceImpl.getAllCustomers().get(0);

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID,testCustomer.getId())
                .content(objectMapper.writeValueAsString(testCustomer))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Stub for the Customer Service sent
        verify(customerService).updateCustomerById(customerCaptor.capture(),uuidCustomerCaptor.capture());

        assertThat(uuidCustomerCaptor.getValue()).isEqualTo(testCustomer.getId());
        assertThat(testCustomer).isEqualTo(customerCaptor.getValue());
    }

    @Test
    void testPatchController() throws Exception {
        Customer testCustomer = customerServiceImpl.getAllCustomers().get(0);

        Map<String,String> patchRequestBody = new HashMap<>();
        patchRequestBody.put("version","1347");
        patchRequestBody.put("customerName","Roxs2552");

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID,testCustomer.getId())
                .content(objectMapper.writeValueAsString(patchRequestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Stub for the Customer Service sent
        verify(customerService).patchCustomerById(customerCaptor.capture(),uuidCustomerCaptor.capture());

        assertThat(uuidCustomerCaptor.getValue()).isEqualTo(testCustomer.getId());
        assertThat("1347").isEqualTo(customerCaptor.getValue().getVersion());
        assertThat("Roxs2552").isEqualTo(customerCaptor.getValue().getCustomerName());
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer testCustomer = customerServiceImpl.getAllCustomers().get(0);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID,testCustomer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Stub for the Customer Service sent
        verify(customerService).deleteCustomerById(uuidCustomerCaptor.capture());

        assertThat(uuidCustomerCaptor.getValue()).isEqualTo(testCustomer.getId());
    }

    @Test
    void getCustomerIdNotFound() throws Exception {

        /* No stub required as Exception handled in customer Controller
        given(customerService.findCustomerById(any(UUID.class))).willThrow(NotFoundException.class); */

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID,UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}