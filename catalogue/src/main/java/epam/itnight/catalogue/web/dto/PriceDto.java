package epam.itnight.catalogue.web.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PriceDto {

  private final String id;
  private final Integer value;
}
