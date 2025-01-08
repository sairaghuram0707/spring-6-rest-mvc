package learn.springframwork.spring6restmvc.services;

import learn.springframwork.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

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
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer c2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Rajesh")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer c3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Vivek")
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
}
