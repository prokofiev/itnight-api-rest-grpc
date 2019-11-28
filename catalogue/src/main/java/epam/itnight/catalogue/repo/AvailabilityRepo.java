package epam.itnight.catalogue.repo;

import epam.itnight.catalogue.domain.Availability;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AvailabilityRepo extends ReactiveMongoRepository<Availability, String> {

}
