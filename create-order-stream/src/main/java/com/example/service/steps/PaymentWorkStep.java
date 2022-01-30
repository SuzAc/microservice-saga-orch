package com.example.service.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.dto.OrderDetails;
import com.example.dto.PaymentResponse;
import com.example.enums.PaymentStatus;
import com.example.enums.WorkFlowStatus;

import reactor.core.publisher.Mono;

public class PaymentWorkStep implements WorkStep {
	private static Logger log = LoggerFactory.getLogger(PaymentWorkStep.class);
	
    private final WebClient webClient;
    private final OrderDetails orderDetails;
    private WorkFlowStatus workFlowStatus = WorkFlowStatus.CREATED;

    public PaymentWorkStep(WebClient webClient, OrderDetails orderDetails) {
        this.webClient = webClient;
        this.orderDetails = orderDetails;
    }

	@Override
	public WorkFlowStatus getStatus() {
		return this.workFlowStatus;
	}

	@Override
	public Mono<Boolean> process() {
		System.out.println("inside p process");
		return this.webClient
					.post()
					.uri("/deduct")
					.body(BodyInserters.fromValue(this.orderDetails))
					.retrieve()
					.bodyToMono(PaymentResponse.class)
					.onErrorContinue((err,obj) -> log.error(err.getMessage()))
					.map(r -> PaymentStatus.SUCCESS.equals(r.getStatus()))
					.doOnNext(st -> this.workFlowStatus = st ? WorkFlowStatus.COMPLETED : WorkFlowStatus.FAILED);
	}

	@Override
	public Mono<Boolean> revert() {
		return this.webClient
				.post()
				.uri("/credit")
				.body(BodyInserters.fromValue(this.orderDetails))
				.retrieve()
				.bodyToMono(Void.class)
				.map(r -> true)
				.onErrorReturn(false);
	}

}
