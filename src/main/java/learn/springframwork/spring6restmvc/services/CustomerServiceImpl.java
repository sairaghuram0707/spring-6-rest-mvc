package learn.springframwork.spring6restmvc.services;

import learn.springframwork.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID,Customer> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        Customer c1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Ram")
                .version("3.4.5")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer c2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Rajesh")
                .version("3.4.6")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer c3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Vivek")
                .version("3.4.7")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        this.customerMap.put(c1.getId(), c1);
        this.customerMap.put(c2.getId(), c2);
        this.customerMap.put(c3.getId(), c3);
    }

    @Override
    public Customer findCustomerById(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(this.customerMap.values());
    }

    @Override
    public Customer addCustomer(Customer customer) {

        // DB Operation Mock
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .version("3.4.6")
                .customerName(customer.getCustomerName())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        this.customerMap.put(customer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public void updateCustomerById(Customer customer, UUID customerId) {

        Customer existingCustomer = this.customerMap.get(customerId);

        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setVersion(customer.getVersion());
        existingCustomer.setLastModifiedDate(customer.getLastModifiedDate());
        existingCustomer.setCreatedDate(customer.getCreatedDate());
    }

    @Override
    public void deleteCustomerById(UUID customerId) {

        this.customerMap.remove(customerId);

    }

    @Override
    public void patchCustomerById(Customer customer, UUID customerId) {

        Customer existingCustomer = this.customerMap.get(customerId);

        if(StringUtils.hasText(customer.getCustomerName())){
            existingCustomer.setCustomerName(customer.getCustomerName());
        }

        if(StringUtils.hasText(customer.getVersion())){
            existingCustomer.setVersion(customer.getVersion());
        }
    }
}
