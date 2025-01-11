package learn.springframwork.spring6restmvc.mappers;

import learn.springframwork.spring6restmvc.entities.Customer;
import learn.springframwork.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer DtoToCustomer(CustomerDTO customer);

    CustomerDTO CustomerToCustomerDto(Customer customer);
}
