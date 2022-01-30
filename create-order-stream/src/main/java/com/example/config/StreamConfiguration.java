package com.example.config;

import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.dto.OrderDetails;
import com.example.service.OrderOrchService;

import reactor.core.publisher.Flux;

@Configuration
public class StreamConfiguration {

	private static Logger log = LoggerFactory.getLogger(StreamConfiguration.class);

	@Autowired
	OrderOrchService orderOrchService;

	@Bean
	public Function<Flux<OrderDetails>, Flux<OrderDetails>> processor() {
		return orderDetails -> {
			return orderDetails.flatMap(orderOrchService::processOrder).map(order -> {
				log.info("Order Creation {}", order.getOrderStatus());
				return order;
			});
		};
	}
}
