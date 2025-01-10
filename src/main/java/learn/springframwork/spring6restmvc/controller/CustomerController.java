package learn.springframwork.spring6restmvc.controller;

import learn.springframwork.spring6restmvc.model.Customer;
import learn.springframwork.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
//@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    @GetMapping(CUSTOMER_PATH)
    public List<Customer> getAllCustomers(){
        return this.customerService.getAllCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId){
        return this.customerService.findCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){

        Customer savedCustomer = this.customerService.addCustomer(customer);

        // Response Headers for POST Request
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location",CUSTOMER_PATH + "/" + savedCustomer.getId());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable("customerId") UUID customerId){

        this.customerService.updateCustomerById(customer, customerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") UUID customerId){
        this.customerService.deleteCustomerById(customerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> patchCustomer(@RequestBody Customer customer, @PathVariable("customerId") UUID customerId){

        this.customerService.patchCustomerById(customer, customerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
