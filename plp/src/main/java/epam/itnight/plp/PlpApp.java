package epam.itnight.plp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(scanBasePackages = "epam.itnight.plp")
@EnableWebFlux
public class PlpApp {

  public static void main(String[] args) {
    SpringApplication.run(PlpApp.class, args);
  }
}
