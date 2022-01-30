package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	@Bean("paymentClient")
	public WebClient paymentClient(@Value("${url.payment}")String  url) {
		return WebClient.builder().baseUrl(url).build();
	}
	
	@Bean("inventoryClient")
	public WebClient inventoryClient(@Value("${url.inventory}")String  url) {
		return WebClient.builder().baseUrl(url).build();
	}

}
