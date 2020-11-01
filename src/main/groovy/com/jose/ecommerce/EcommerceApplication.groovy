package com.jose.ecommerce

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableJpaRepositories("com.jose.ecommerce.model.persistence.repositories")
@EntityScan("com.jose.ecommerce.model.persistence")
@SpringBootApplication()
class EcommerceApplication {

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	static void main(String[] args) {
		SpringApplication.run(EcommerceApplication, args)
	}

}