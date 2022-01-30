package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OrderDetails;
import com.example.dto.OrderDetailsEntity;
import com.example.service.OrderService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/${application.version}")
public class Controller {
    @Autowired
    private OrderService orderService;
	
	@PostMapping("/create")
	public ResponseEntity<String> createOrder(@RequestBody List<OrderDetails> orderList){
		orderService.createOrder(orderList);
		return ResponseEntity.ok("Ok");
	}
	
	@GetMapping("/find")
	public Flux<OrderDetailsEntity> getAll(){
		return orderService.getAllOrders();
	}

}
