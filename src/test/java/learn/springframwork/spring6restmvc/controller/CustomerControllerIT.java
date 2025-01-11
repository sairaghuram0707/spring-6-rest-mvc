package learn.springframwork.spring6restmvc.controller;

import jakarta.transaction.Transactional;
import learn.springframwork.spring6restmvc.entities.Customer;
import learn.springframwork.spring6restmvc.mappers.CustomerMapper;
import learn.springframwork.spring6restmvc.model.CustomerDTO;
import learn.springframwork.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Rollback
    @Transactional
    @Test
    void emptyCustomerList(){

        customerRepository.deleteAll();

        assertThat(customerController.getAllCustomers()).isEmpty();
    }

    @Test
    void testGetCustomerException(){

        assertThrows(NotFoundException.class,()->{
            customerController.getCustomerById(UUID.randomUUID());
        });

    }

    @Test
    void testCustomerById(){

        Customer customer = customerRepository.findAll().get(0);

        assertThat(customerController.getCustomerById(customer.getId()).getCustomerName()).isEqualTo(customer.getCustomerName());
    }

    @Transactional
    @Rollback
    @Test
    void testAddNewCustomer() {

        CustomerDTO newCustomer = CustomerDTO.builder()
                .customerName("Test Customer 69")
                .createdDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        ResponseEntity<CustomerDTO> responseEntity = customerController.createCustomer(newCustomer);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        String[] locationUuid = responseEntity.getHeaders().getLocation().getPath().split("/" );
        UUID uuid = UUID.fromString(locationUuid[locationUuid.length-1]);

        CustomerDTO savedCustomer= customerController.getCustomerById(uuid);
        assertThat(savedCustomer.getCustomerName()).isEqualTo(newCustomer.getCustomerName());
    }

    @Test
    void deleteCustomerByIdNotFound() {

        assertThrows(NotFoundException.class,()->{
            customerController.deleteCustomer(UUID.randomUUID());
        });

    }

    @Transactional
    @Rollback
    @Test
    void deleteCustomerByIdFound() {
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity responseEntity = customerController.deleteCustomer(customer.getId());

        assertThat(customerRepository.findById(customer.getId())).isEmpty();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void updateCustomerByIdFailed() {
        assertThrows(NotFoundException.class,()->{
            customerController.updateCustomer(
                    CustomerDTO.builder().build(),UUID.randomUUID()
            );
        });
    }

    @Transactional
    @Rollback
    @Test
    void updateCustomerById() {
        final String BEER_NAME = "Test Name";
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.CustomerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        customerDTO.setCustomerName(BEER_NAME);

        ResponseEntity responseEntity = customerController.updateCustomer(customerDTO,customer.getId());

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        CustomerDTO savedCustomer= customerController.getCustomerById(customer.getId());
        assertThat(savedCustomer.getCustomerName()).isEqualTo(BEER_NAME);
    }


}