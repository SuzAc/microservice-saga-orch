package com.example.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.OrderDetails;
import com.example.repository.OrderRepository;

@Service
public class UpdateOrderService {
	private static Logger log = LoggerFactory.getLogger(UpdateOrderService.class);
	
	@Autowired
	private OrderRepository orderRepository;

	public void updateOrder(OrderDetails order) {
		orderRepository.findById(order.getId().toString()).subscribe(orderEntity -> {
			orderEntity.setOrderStatus(order.getOrderStatus());
			orderEntity.setUpdatedAt(LocalDateTime.now());
			orderRepository.save(orderEntity).subscribe();
			log.info("Updated with status: {}",order.getOrderStatus());
		});
	}

}
