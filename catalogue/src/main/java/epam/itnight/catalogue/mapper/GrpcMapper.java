package epam.itnight.catalogue.mapper;

import static java.util.stream.Collectors.toList;

import epam.itnight.catalogue.domain.Availability;
import epam.itnight.catalogue.domain.Price;
import epam.itnight.catalogue.domain.Product;
import epam.itnight.catalogue.grpc.ProtoProducts;
import java.util.List;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GrpcMapper {

  default ProtoProducts.IdList map(List<String> idListByCategory) {

    List<ProtoProducts.Id> list = idListByCategory.stream()
        .map(this::map)
        .collect(toList());
    return ProtoProducts.IdList.newBuilder().addAllIds(list).build();
  }

  ProtoProducts.Id map(String id);

  ProtoProducts.Product map(Product product);

  ProtoProducts.Price map(Price price);

  ProtoProducts.Availability map(Availability availability);
}
