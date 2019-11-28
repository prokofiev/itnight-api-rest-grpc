package epam.itnight.catalogue.service;

import epam.itnight.catalogue.domain.Availability;
import epam.itnight.catalogue.domain.Price;
import epam.itnight.catalogue.domain.Product;
import epam.itnight.catalogue.repo.AvailabilityRepo;
import epam.itnight.catalogue.repo.PriceRepo;
import epam.itnight.catalogue.repo.ProductRepo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

  private final ProductRepo productRepo;
  private final PriceRepo priceRepo;
  private final AvailabilityRepo availabilityRepo;

  public ProductService(ProductRepo productRepo, PriceRepo priceRepo,
      AvailabilityRepo availabilityRepo) {
    this.productRepo = productRepo;
    this.priceRepo = priceRepo;
    this.availabilityRepo = availabilityRepo;
  }

  public Mono<Product> findProductById(String id) {
    return productRepo.findById(id);
  }

  public Mono<Price> getPrice(String id) {
    return priceRepo.findById(id);
  }

  public Mono<Availability> getAvailability(String id) {
    return availabilityRepo.findById(id);
  }

  public Flux<String> findIdListByCategory(String category) {
    return productRepo.findAllByCategory(category)
        .map(Product::getId);
  }
}
