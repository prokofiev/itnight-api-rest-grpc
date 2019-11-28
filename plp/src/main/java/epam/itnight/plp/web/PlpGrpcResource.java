package epam.itnight.plp.web;

import epam.itnight.plp.domain.ProductItem;
import epam.itnight.plp.service.CatalogueGrpcService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "/grpc", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlpGrpcResource {

  private final CatalogueGrpcService catalogueGrpcService;

  public PlpGrpcResource(CatalogueGrpcService catalogueGrpcService) {
    this.catalogueGrpcService = catalogueGrpcService;
  }


  @GetMapping("/getElementsByCategory/{category}")
  public Flux<ProductItem> grpcGetIdListByCategory(@PathVariable String category) {
    return catalogueGrpcService.getElementsByCategory(category);
  }
}
