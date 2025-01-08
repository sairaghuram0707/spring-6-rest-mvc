package learn.springframwork.spring6restmvc.services;

import learn.springframwork.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer findCustomerById(UUID id);

    List<Customer> getAllCustomers();

    Customer addCustomer(Customer customer);
}
