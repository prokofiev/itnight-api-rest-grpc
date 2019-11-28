package epam.itnight.plp.service;

import epam.itnight.catalogue.api.ProductResourceApi;
import epam.itnight.catalogue.model.AvailabilityDto;
import epam.itnight.catalogue.model.PriceDto;
import epam.itnight.catalogue.model.ProductDto;
import epam.itnight.plp.domain.ProductItem;
import epam.itnight.plp.domain.ProductItem.ProductItemBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@ConditionalOnBean(ProductResourceApi.class)
public class CatalogueRestService {

  private final ProductResourceApi productApi;

  public CatalogueRestService(ProductResourceApi productApi) {
    this.productApi = productApi;
  }

  private static ProductItemBuilder createElement(ProductDto product) {
    return ProductItem.builder()
        .id(product.getId())
        .category(product.getCategory())
        .name(product.getName());
  }

  private static ProductItemBuilder setPrice(ProductItemBuilder itemBuilder, PriceDto price) {
    return itemBuilder.price(price.getValue());
  }

  private static ProductItemBuilder setAvailability(ProductItemBuilder itemBuilder,
      AvailabilityDto availability) {
    return itemBuilder.availability(availability.getAvailable());
  }

  public Flux<ProductItem> getElementsByCategory(String category) {
    return productApi.getIdListByCategoryUsingGET(category)
        .flatMapMany(idListDto -> Flux.fromIterable(idListDto.getValues()))
        .flatMap(this::getProductItem);
  }

  private Mono<ProductItem> getProductItem(String id) {
    return productApi.getByIdUsingGET(id)
        .map(CatalogueRestService::createElement)
        .zipWith(productApi.getPriceUsingGET(id), CatalogueRestService::setPrice)
        .zipWith(productApi.getAvailabilityUsingGET(id), CatalogueRestService::setAvailability)
        .map(ProductItemBuilder::build);
  }
}
