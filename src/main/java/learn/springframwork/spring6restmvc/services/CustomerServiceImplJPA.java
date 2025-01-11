package learn.springframwork.spring6restmvc.services;

import learn.springframwork.spring6restmvc.mappers.CustomerMapper;
import learn.springframwork.spring6restmvc.model.CustomerDTO;
import learn.springframwork.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
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
        return customerMapper.CustomerToCustomerDto(
                customerRepository.save(customerMapper.DtoToCustomer(customer))
        );
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(CustomerDTO customer, UUID customerId) {

        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        // Lambda Function
        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setCustomerName(customer.getCustomerName());
            atomicReference.set(Optional.of(customerMapper
            .CustomerToCustomerDto(customerRepository.save(foundCustomer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        if(customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return true;
        }   return false;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(CustomerDTO customer, UUID customerId) {

        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        // Lambda Function
        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            if(StringUtils.hasText(customer.getCustomerName())){
                foundCustomer.setCustomerName(customer.getCustomerName());
            }
            atomicReference.set(Optional.of(customerMapper.
                    CustomerToCustomerDto(customerRepository.save(foundCustomer))));
        },()->{
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }
}
