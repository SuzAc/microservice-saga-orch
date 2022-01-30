package com.example.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.dto.OrderDetailsEntity;

public interface OrderRepository extends ReactiveMongoRepository<OrderDetailsEntity, String>{

}
