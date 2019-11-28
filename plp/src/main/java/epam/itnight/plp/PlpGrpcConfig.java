package epam.itnight.plp;

import epam.itnight.catalogue.grpc.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlpGrpcConfig {

  private ManagedChannel channel;

  @Bean
  public ProductServiceGrpc.ProductServiceStub productsGrpcService(
      @Value("${api.catalogue.transport.grpc}") String target) {
    channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
    return ProductServiceGrpc.newStub(channel);
  }

  @PreDestroy
  public void shutdown() {
    if (channel != null) {
      channel.shutdown();
      channel = null;
    }
  }
}
