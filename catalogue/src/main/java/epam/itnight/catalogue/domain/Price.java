package epam.itnight.catalogue.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("price")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Price {

  @Id
  private String id;
  private Integer value;
}
