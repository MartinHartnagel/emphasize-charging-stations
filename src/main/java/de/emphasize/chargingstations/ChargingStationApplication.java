package de.emphasize.chargingstations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@EnableSwagger2
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ChargingStationApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChargingStationApplication.class, args);
	}
}
