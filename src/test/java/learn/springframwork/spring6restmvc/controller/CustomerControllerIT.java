package learn.springframwork.spring6restmvc.controller;

import jakarta.transaction.Transactional;
import learn.springframwork.spring6restmvc.entities.Customer;
import learn.springframwork.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;


    @Rollback
    @Transactional
    @Test
    void emptyCustomerList(){

        customerRepository.deleteAll();

        assertThat(customerController.getAllCustomers()).isEmpty();
    }

    @Test
    void testCustomerById(){

        Customer customer = customerRepository.findAll().get(0);

        assertThat(customerController.getCustomerById(customer.getId()).getCustomerName()).isEqualTo(customer.getCustomerName());
    }

    @Test
    void testGetCustomerException(){

        assertThrows(NotFoundException.class,()->{
          customerController.getCustomerById(UUID.randomUUID());
        });

    }
}