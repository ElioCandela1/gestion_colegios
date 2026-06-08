package com.sistema_colegios.gestion_colegios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GestionColegiosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionColegiosApplication.class, args);
	}

}
