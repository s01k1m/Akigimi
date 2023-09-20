package com.kangkimleekojangcho.akgimi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AkgimiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AkgimiApplication.class, args);
	}
}
