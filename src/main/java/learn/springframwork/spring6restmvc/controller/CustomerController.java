package learn.springframwork.spring6restmvc.controller;

import learn.springframwork.spring6restmvc.model.CustomerDTO;
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
    public List<CustomerDTO> getAllCustomers(){
        return this.customerService.getAllCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId){
        return this.customerService.findCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customer){

        CustomerDTO savedCustomer = this.customerService.addCustomer(customer);

        // Response Headers for POST Request
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location",CUSTOMER_PATH + "/" + savedCustomer.getId());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customer, @PathVariable("customerId") UUID customerId){

        if(this.customerService.updateCustomerById(customer, customerId).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable("customerId") UUID customerId){
        if(this.customerService.deleteCustomerById(customerId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new NotFoundException("Customer not found");
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> patchCustomer(@RequestBody CustomerDTO customer, @PathVariable("customerId") UUID customerId){

        this.customerService.patchCustomerById(customer, customerId).orElseThrow(NotFoundException::new);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
