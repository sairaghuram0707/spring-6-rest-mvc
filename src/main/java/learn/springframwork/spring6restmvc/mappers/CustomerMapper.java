package learn.springframwork.spring6restmvc.mappers;

import learn.springframwork.spring6restmvc.entities.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer DtoToCustomer(Customer customer);

    Customer CustomerToDto(Customer customer);
}
