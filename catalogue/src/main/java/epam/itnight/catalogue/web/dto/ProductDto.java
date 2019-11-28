package epam.itnight.catalogue.web.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductDto {

  private final String id;
  private final String category;
  private final String name;
}
