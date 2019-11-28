package epam.itnight.plp.web;

import epam.itnight.plp.domain.ProductItem;
import epam.itnight.plp.service.CatalogueRestService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlpRestResource {

  private final CatalogueRestService catalogueRestService;

  public PlpRestResource(CatalogueRestService catalogueRestService) {
    this.catalogueRestService = catalogueRestService;
  }


  @GetMapping("/getElementsByCategory/{category}")
  public Flux<ProductItem> restGetIdListByCategory(@PathVariable String category) {
    return catalogueRestService.getElementsByCategory(category);
  }
}
