package epam.itnight.plp.service;

import epam.itnight.catalogue.grpc.ProductServiceGrpc;
import epam.itnight.catalogue.grpc.ProtoProducts.Availability;
import epam.itnight.catalogue.grpc.ProtoProducts.Id;
import epam.itnight.catalogue.grpc.ProtoProducts.IdList;
import epam.itnight.catalogue.grpc.ProtoProducts.Price;
import epam.itnight.catalogue.grpc.ProtoProducts.Product;
import epam.itnight.plp.domain.ProductItem;
import epam.itnight.plp.domain.ProductItem.ProductItemBuilder;
import epam.itnight.plp.util.StreamObserverAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

@Service
@ConditionalOnBean(ProductServiceGrpc.ProductServiceStub.class)
public class CatalogueGrpcService {

  private final ProductServiceGrpc.ProductServiceStub productsGrpcService;

  public CatalogueGrpcService(ProductServiceGrpc.ProductServiceStub productsGrpcService) {
    this.productsGrpcService = productsGrpcService;
  }

  private static ProductItemBuilder createProductItem(Product product) {
    return ProductItem.builder()
        .id(product.getId())
        .category(product.getCategory())
        .name(product.getName());
  }

  private static ProductItemBuilder setPrice(ProductItemBuilder itemBuilder, Price price) {
    return itemBuilder.price(price.getValue());
  }

  private static ProductItemBuilder setAvailability(ProductItemBuilder itemBuilder,
      Availability availability) {
    return itemBuilder.availability(availability.getAvailable());
  }

  public Flux<ProductItem> getElementsByCategory(String category) {
    Id categoryId = Id.newBuilder().setId(category).build();

    Mono<IdList> idListMono = Mono.create((MonoSink<IdList> sink) ->
        productsGrpcService.findProductIdList(categoryId, new StreamObserverAdapter<>(sink)));

    return idListMono
        .map(IdList::getIdsList)
        .flatMapMany(Flux::fromIterable)
        .flatMap(this::getProductItem);
  }

  private Mono<ProductItem> getProductItem(Id id) {
    Mono<Product> productMono = Mono.create(sink ->
        productsGrpcService.findProductById(id, new StreamObserverAdapter<>(sink)));

    Mono<Price> priceMono = Mono.create(sink ->
        productsGrpcService.getPrice(id, new StreamObserverAdapter<>(sink)));

    Mono<Availability> availabilityMono = Mono.create(sink ->
        productsGrpcService.getAvailability(id, new StreamObserverAdapter<>(sink)));

    return productMono
        .map(CatalogueGrpcService::createProductItem)
        .zipWith(priceMono, CatalogueGrpcService::setPrice)
        .zipWith(availabilityMono, CatalogueGrpcService::setAvailability)
        .map(ProductItemBuilder::build);
  }
}
