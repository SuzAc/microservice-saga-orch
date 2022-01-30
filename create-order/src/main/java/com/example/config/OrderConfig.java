package com.example.config;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.dto.OrderDetails;
import com.example.service.OrderService;
import com.example.service.UpdateOrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class OrderConfig {
	
    @Autowired
    private UpdateOrderService updateOrderService;

    @Bean
    public Sinks.Many<OrderDetails> orderSink(){
        return Sinks.many().multicast().onBackpressureBuffer();
    }
    
    @Bean
    public Supplier<Flux<OrderDetails>> createOrder(Sinks.Many<OrderDetails> sink){
        return sink::asFlux;
    }
    
    @Bean
    public Consumer<Flux<OrderDetails>> updateOrder(){
        return (flux) -> flux
                            .subscribe(order -> updateOrderService.updateOrder(order));
    };

}
