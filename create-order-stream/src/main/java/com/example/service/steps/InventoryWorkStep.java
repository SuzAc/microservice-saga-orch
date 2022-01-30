package com.example.service.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.dto.InventoryResponse;
import com.example.dto.OrderDetails;
import com.example.enums.InventoryStatus;
import com.example.enums.WorkFlowStatus;

import reactor.core.publisher.Mono;

public class InventoryWorkStep implements WorkStep {
	
	private static Logger log = LoggerFactory.getLogger(InventoryWorkStep.class);

	
    private final WebClient webClient;
    private final OrderDetails orderDetails;
    private WorkFlowStatus workFlowStatus = WorkFlowStatus.CREATED;

    public InventoryWorkStep(WebClient webClient, OrderDetails orderDetails) {
        this.webClient = webClient;
        this.orderDetails = orderDetails;
    }

	@Override
	public WorkFlowStatus getStatus() {
		return this.workFlowStatus;
	}

	@Override
	public Mono<Boolean> process() {
		System.out.println("inside process");
		return this.webClient
					.post()
					.uri("/reduce")
					.body(BodyInserters.fromValue(this.orderDetails))
					.retrieve()
					.bodyToMono(InventoryResponse.class)
					.onErrorContinue((err,obj) -> log.error(err.getMessage()))
					.map(r -> InventoryStatus.AVAILABLE.equals(r.getStatus()))
					.doOnNext(st -> this.workFlowStatus = st ? WorkFlowStatus.COMPLETED : WorkFlowStatus.FAILED);
	}

	@Override
	public Mono<Boolean> revert() {
		return this.webClient
				.post()
				.uri("/add")
				.body(BodyInserters.fromValue(this.orderDetails))
				.retrieve()
				.bodyToMono(Void.class)
				.map(r -> true)
				.onErrorReturn(false);
	}
}
