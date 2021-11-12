package com.devland.walletapi;

// import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WalletApiApplication {

	// @Bean
	// public ModelMapper modelMapper() {
	// 	return new ModelMapper();
	// }

	public static void main(String[] args) {
		SpringApplication.run(WalletApiApplication.class, args);
	}

}
