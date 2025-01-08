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
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getAllCustomers(){
        return this.customerService.getAllCustomers();
    }

    @RequestMapping(value = "/{customerId}",method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId){
        return this.customerService.findCustomerById(customerId);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){

        Customer savedCustomer = this.customerService.addCustomer(customer);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location","/api/v1/customer" + savedCustomer.getId());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}
