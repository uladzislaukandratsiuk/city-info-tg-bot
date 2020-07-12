package com.city.info.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class CityInfoTgBotApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(CityInfoTgBotApplication.class, args);
	}

}
