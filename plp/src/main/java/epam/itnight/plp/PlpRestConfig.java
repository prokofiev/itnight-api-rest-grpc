package epam.itnight.plp;

import epam.itnight.catalogue.ApiClient;
import epam.itnight.catalogue.api.ProductResourceApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlpRestConfig {

  @Bean
  public ProductResourceApi productResourceApi(
      @Value("${api.catalogue.transport.rest}") String url) {
    ApiClient apiClient = new ApiClient();
    apiClient.setBasePath(url);
    return new ProductResourceApi(apiClient);
  }
}
