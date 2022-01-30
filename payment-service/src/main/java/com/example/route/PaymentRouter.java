package com.example.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.service.PaymentService;

@Configuration
@EnableWebFlux
public class PaymentRouter {
	
	@Value("/${application.version}")
	String version;

	@Bean
	RouterFunction<ServerResponse> router(PaymentService paymentService) {
		return RouterFunctions.nest(RequestPredicates.path(version),
				RouterFunctions.route(RequestPredicates.POST("/deduct").and(RequestPredicates.accept(MediaType.TEXT_EVENT_STREAM)), paymentService::deduct)
						.andRoute(RequestPredicates.POST("/credit"), paymentService::credit)
						.andRoute(RequestPredicates.GET("/findbal"), paymentService::findBalance));
	}

}
