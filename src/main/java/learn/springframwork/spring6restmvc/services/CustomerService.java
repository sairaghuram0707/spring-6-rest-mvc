package learn.springframwork.spring6restmvc.services;

import learn.springframwork.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Optional<CustomerDTO> findCustomerById(UUID id);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO addCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerById(CustomerDTO customer, UUID customerId);

    Boolean deleteCustomerById(UUID customerId);

    void patchCustomerById(CustomerDTO customer, UUID customerId);
}
