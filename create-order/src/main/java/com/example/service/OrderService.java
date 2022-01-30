package com.example.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.OrderDetails;
import com.example.dto.OrderDetailsEntity;
import com.example.enums.OrderStatus;
import com.example.repository.OrderRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class OrderService {
	
	private static Logger log = LoggerFactory.getLogger(OrderService.class);
	
    @Autowired
    private Sinks.Many<OrderDetails> orderSink;
    
    @Autowired
    private OrderRepository orderRepository;
    
    public void createOrder(List<OrderDetails> orderList) {
		orderList.forEach(order -> {
	    orderRepository.save(getOrderEntity(order)).subscribe();
		log.info("created with record id: {}",order.getId());
		orderSink.emitNext(order, null);
		});
    	
    }
    
    private OrderDetailsEntity getOrderEntity(OrderDetails order) {
    	OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity();
    	orderDetailsEntity.setId(order.getId().toString());
    	orderDetailsEntity.setUserId(order.getUserId());
    	orderDetailsEntity.setProductId(order.getProductId());
    	orderDetailsEntity.setPrice(order.getPrice());
    	orderDetailsEntity.setCreatedAt(LocalDateTime.now());
    	orderDetailsEntity.setUpdatedAt(orderDetailsEntity.getCreatedAt());
    	orderDetailsEntity.setOrderStatus(OrderStatus.ORDER_CREATED);
    	return orderDetailsEntity;
	}

	public Flux<OrderDetailsEntity> getAllOrders() {
    	return orderRepository.findAll();
    }

}
