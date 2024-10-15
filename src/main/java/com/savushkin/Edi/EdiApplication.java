package com.savushkin.Edi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class EdiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdiApplication.class, args);
	}

}
