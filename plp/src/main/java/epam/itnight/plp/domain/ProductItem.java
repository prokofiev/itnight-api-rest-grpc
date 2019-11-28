package epam.itnight.plp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class ProductItem {

  private final String id;
  private final String category;
  private final String name;
  private final Integer price;
  private final Integer availability;
}
