package ch.juventus.car_rental;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class CarRentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI().info(new Info()
                .title("Car Rental API")
                .version(appVersion)
                .description("This is an api for a car rental plattform written with Spring Boot.")
                .license(new License().name("MIT License")
                        .url("https://github.com/YvesCB/hs24-tsi-car-rental?tab=MIT-1-ov-file")));
    }
}
