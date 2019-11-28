package epam.itnight.catalogue.web;

import epam.itnight.catalogue.mapper.DtoMapper;
import epam.itnight.catalogue.service.ProductService;
import epam.itnight.catalogue.web.dto.AvailabilityDto;
import epam.itnight.catalogue.web.dto.IdListDto;
import epam.itnight.catalogue.web.dto.PriceDto;
import epam.itnight.catalogue.web.dto.ProductDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductResource {

  private final ProductService productService;
  private final DtoMapper dtoMapper;

  public ProductResource(ProductService productService, DtoMapper dtoMapper) {
    this.productService = productService;
    this.dtoMapper = dtoMapper;
  }

  @GetMapping("/getIdListByCategory/{category}")
  public Mono<IdListDto> getIdListByCategory(@PathVariable String category) {
    return productService.findIdListByCategory(category)
        .collectList()
        .map(dtoMapper::map);
  }

  @GetMapping("/get/{id}")
  public Mono<ProductDto> getById(@PathVariable String id) {
    return productService.findProductById(id)
        .map(dtoMapper::map);
  }

  @GetMapping("/getPrice/{id}")
  public Mono<PriceDto> getPrice(@PathVariable String id) {
    return productService.getPrice(id)
        .map(dtoMapper::map);
  }

  @GetMapping("/getAvailability/{id}")
  public Mono<AvailabilityDto> getAvailability(@PathVariable String id) {
    return productService.getAvailability(id)
        .map(dtoMapper::map);
  }
}
