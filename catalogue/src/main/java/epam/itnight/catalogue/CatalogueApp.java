package epam.itnight.catalogue;

import epam.itnight.catalogue.domain.Availability;
import epam.itnight.catalogue.domain.Price;
import epam.itnight.catalogue.domain.Product;
import epam.itnight.catalogue.repo.AvailabilityRepo;
import epam.itnight.catalogue.repo.PriceRepo;
import epam.itnight.catalogue.repo.ProductRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

@SpringBootApplication(scanBasePackages = "epam.itnight.catalogue")
@EnableWebFlux
public class CatalogueApp implements CommandLineRunner {

  private static final String PHONE = "phone";
  private static final String TV = "tv";

  private final ProductRepo productRepo;
  private final PriceRepo priceRepo;
  private final AvailabilityRepo availabilityRepo;


  public CatalogueApp(ProductRepo productRepo, PriceRepo priceRepo,
      AvailabilityRepo availabilityRepo) {
    this.productRepo = productRepo;
    this.priceRepo = priceRepo;
    this.availabilityRepo = availabilityRepo;
  }

  public static void main(String[] args) {
    SpringApplication.run(CatalogueApp.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    productRepo.deleteAll()
        .then(priceRepo.deleteAll())
        .then(availabilityRepo.deleteAll())
        .then(createProduct("0001", PHONE, "Samsung galaxy s 10", 1000, 3))
        .then(createProduct("0002", PHONE, "Apple iphone x", 1000, 5))
        .then(createProduct("0003", PHONE, "Honor 20 pro", 700, 1))
        .then(createProduct("0004", PHONE, "Huawei p30", 900, 2))
        .then(createProduct("0005", PHONE, "Xiaomi mi9", 700, 8))
        .then(createProduct("0006", TV, "Samsung 8K smart QLED", 3000, 3))
        .then(createProduct("0007", TV, "LG OLED 4K Cinema", 2000, 1))
        .then(createProduct("0008", TV, "Sony Class LED Z9G MASTER", 2000, 2))
        .block();
  }

  private Mono<Product> createProduct(String id, String category, String name, int price,
      int available) {
    return availabilityRepo.save(new Availability(id, available))
        .then(priceRepo.save(new Price(id, price)))
        .then(productRepo.save(new Product(id, category, name)));
  }
}
