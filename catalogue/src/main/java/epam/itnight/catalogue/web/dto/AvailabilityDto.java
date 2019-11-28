package epam.itnight.catalogue.web.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AvailabilityDto {

  private final String id;
  private final Integer available;
}
