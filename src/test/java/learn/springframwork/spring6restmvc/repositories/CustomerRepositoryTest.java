package learn.springframwork.spring6restmvc.repositories;


import learn.springframwork.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void saveCustomer() {
        Customer customer = customerRepository.save(
                Customer.builder()
                        .customerName("Test customer")
                        .build()
        );

        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getCustomerName()).isEqualTo("Test customer");
    }

}