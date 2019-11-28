package epam.itnight.catalogue.repo;

import epam.itnight.catalogue.domain.Price;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepo extends ReactiveMongoRepository<Price, String> {

}
