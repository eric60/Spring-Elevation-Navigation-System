package com.EleNa;

import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EleNaApplication {
	private final static Logger LOGGER = Logger.getLogger(EleNaApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(EleNaApplication.class, args);
		LOGGER.info("test info log");
	}

}
