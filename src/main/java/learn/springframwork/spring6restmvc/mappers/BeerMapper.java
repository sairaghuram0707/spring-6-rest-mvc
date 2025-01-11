package learn.springframwork.spring6restmvc.mappers;

import learn.springframwork.spring6restmvc.entities.Beer;
import learn.springframwork.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDTO(Beer beer);
}
