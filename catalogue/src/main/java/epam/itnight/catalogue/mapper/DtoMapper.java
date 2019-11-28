package epam.itnight.catalogue.mapper;

import epam.itnight.catalogue.domain.Availability;
import epam.itnight.catalogue.domain.Price;
import epam.itnight.catalogue.domain.Product;
import epam.itnight.catalogue.web.dto.AvailabilityDto;
import epam.itnight.catalogue.web.dto.IdListDto;
import epam.itnight.catalogue.web.dto.PriceDto;
import epam.itnight.catalogue.web.dto.ProductDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DtoMapper {

  ProductDto map(Product product);

  PriceDto map(Price price);

  AvailabilityDto map(Availability availability);

  default IdListDto map(List<String> strings) {
    return new IdListDto(strings);
  }
}
