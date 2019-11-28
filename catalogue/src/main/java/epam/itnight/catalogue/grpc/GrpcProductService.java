package epam.itnight.catalogue.grpc;

import epam.itnight.catalogue.grpc.ProtoProducts.Availability;
import epam.itnight.catalogue.grpc.ProtoProducts.IdList;
import epam.itnight.catalogue.grpc.ProtoProducts.Price;
import epam.itnight.catalogue.grpc.ProtoProducts.Product;
import epam.itnight.catalogue.mapper.GrpcMapper;
import epam.itnight.catalogue.service.ProductService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import reactor.core.publisher.Mono;

@GRpcService
public class GrpcProductService extends ProductServiceGrpc.ProductServiceImplBase {

  private final ProductService productService;
  private final GrpcMapper grpcMapper;

  public GrpcProductService(ProductService productService,
      GrpcMapper grpcMapper) {

    this.productService = productService;
    this.grpcMapper = grpcMapper;
  }

  @Override
  public void findProductIdList(ProtoProducts.Id request,
      StreamObserver<ProtoProducts.IdList> responseObserver) {

    Mono<IdList> mono = productService.findIdListByCategory(request.getId())
        .collectList()
        .map(grpcMapper::map);
    monoToObserver(mono, responseObserver);
  }

  @Override
  public void findProductById(ProtoProducts.Id request,
      StreamObserver<ProtoProducts.Product> responseObserver) {

    Mono<Product> mono = productService.findProductById(request.getId())
        .map(grpcMapper::map);
    monoToObserver(mono, responseObserver);
  }

  @Override
  public void getPrice(ProtoProducts.Id request,
      StreamObserver<ProtoProducts.Price> responseObserver) {

    Mono<Price> mono = productService.getPrice(request.getId())
        .map(grpcMapper::map);
    monoToObserver(mono, responseObserver);
  }

  @Override
  public void getAvailability(ProtoProducts.Id request,
      StreamObserver<ProtoProducts.Availability> responseObserver) {

    Mono<Availability> mono = productService.getAvailability(request.getId())
        .map(grpcMapper::map);
    monoToObserver(mono, responseObserver);
  }

  private <T> void monoToObserver(Mono<T> mono, StreamObserver<T> observer) {
    mono.doOnError(e -> {
      observer.onError(e);
      observer.onCompleted();
    }).subscribe(data -> {
      observer.onNext(data);
      observer.onCompleted();
    });
  }
}