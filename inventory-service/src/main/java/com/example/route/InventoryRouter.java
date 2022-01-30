package com.example.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Version;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.service.InventoryService;

@Configuration
public class InventoryRouter {
	
	@Value("/${application.version}")
	String version;
	
	
	@Bean
	public RouterFunction<ServerResponse> inventoryRoute(InventoryService inventoryService) {
		return RouterFunctions.nest(RequestPredicates.path(version),
				RouterFunctions.route(RequestPredicates.POST("/reduce"), inventoryService::reduce)
				.andRoute(RequestPredicates.POST("/add"), inventoryService::add)
				.andRoute(RequestPredicates.GET("/findinv"), inventoryService::invDetails));
	}

}
