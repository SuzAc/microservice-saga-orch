package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.dto.InventoryResponse;
import com.example.dto.OrderDetails;
import com.example.enums.InventoryStatus;
import com.example.repo.InventoryStaticRepository;

import reactor.core.publisher.Mono;

@Service
public class InventoryService {

	private static Logger log = LoggerFactory.getLogger(InventoryService.class);

	@Autowired
	InventoryStaticRepository inventoryStaticRepository;

	public Mono<ServerResponse> reduce(ServerRequest serverRequest) {
		log.info("Inventory reduction starting");
		return serverRequest.bodyToMono(OrderDetails.class).flatMap(req -> {
			InventoryResponse inventoryResponse = new InventoryResponse();
			inventoryResponse
					.setStatus(inventoryStaticRepository.deductCount(req.getProductId()) ? InventoryStatus.AVAILABLE
							: InventoryStatus.NOT_AVAILABLE);
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(inventoryResponse);
		});
	}

	public Mono<ServerResponse> add(ServerRequest serverRequest) {
		log.info("Inventory add starting");
		return serverRequest.bodyToMono(OrderDetails.class).flatMap(req -> {
			InventoryResponse inventoryResponse = new InventoryResponse();
			inventoryResponse.setStatus(InventoryStatus.AVAILABLE);
			inventoryStaticRepository.addCount(req.getProductId());
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(inventoryResponse);
		});
	}

	public Mono<ServerResponse> invDetails(ServerRequest serverRequest) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(inventoryStaticRepository.repoInv);
	}
}
