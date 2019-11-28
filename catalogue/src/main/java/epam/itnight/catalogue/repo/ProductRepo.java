package epam.itnight.catalogue.repo;

import epam.itnight.catalogue.domain.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepo extends ReactiveMongoRepository<Product, String> {

  Flux<Product> findAllByCategory(String category);
}
