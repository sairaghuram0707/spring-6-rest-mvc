package learn.springframwork.spring6restmvc.services;

import learn.springframwork.spring6restmvc.mappers.CustomerMapper;
import learn.springframwork.spring6restmvc.model.CustomerDTO;
import learn.springframwork.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// Interaction With the Customer Repository Layer handled in the Implementation
@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceImplJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDTO> findCustomerById(UUID id) {
        return Optional.ofNullable(
                customerMapper.CustomerToCustomerDto(customerRepository.findById(id).orElse(null))
        );
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::CustomerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateCustomerById(CustomerDTO customer, UUID customerId) {

    }

    @Override
    public void deleteCustomerById(UUID customerId) {

    }

    @Override
    public void patchCustomerById(CustomerDTO customer, UUID customerId) {

    }
}
