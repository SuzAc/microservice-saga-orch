package com.example.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.dto.OrderDetails;
import com.example.enums.OrderStatus;
import com.example.enums.WorkFlowStatus;
import com.example.service.steps.InventoryWorkStep;
import com.example.service.steps.PaymentWorkStep;
import com.example.service.steps.WorkStep;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderOrchService {

	private static Logger log = LoggerFactory.getLogger(OrderOrchService.class);

	@Autowired
	@Qualifier("paymentClient")
	WebClient paymentClient;

	@Autowired
	@Qualifier("inventoryClient")
	WebClient inventoryClient;

	public Mono<OrderDetails> processOrder(OrderDetails orderDetails) {
		log.info("service processing");
		List<WorkStep> workStepList = List.of(new PaymentWorkStep(paymentClient, orderDetails),
				new InventoryWorkStep(inventoryClient, orderDetails));
		return Flux.fromStream(workStepList.stream()).flatMap(ws -> ws.process()).handle((result, syncLink) -> {
			if (!result)
				syncLink.error(new Exception("failure from third party"));
		}).log().then(Mono.just(getOrderObject(orderDetails, true))).onErrorResume(error -> {
			log.error(error.getMessage());
			return revertOrder(workStepList, orderDetails);
		});
	}

	private Mono<OrderDetails> revertOrder(final List<WorkStep> workStepList, final OrderDetails requestDTO) {
		return Flux.fromStream(() -> workStepList.stream())
				.filter(wf -> wf.getStatus().equals(WorkFlowStatus.COMPLETED)).flatMap(WorkStep::revert).retry(3)
				.then(Mono.just(getOrderObject(requestDTO, false)));
	}

	private OrderDetails getOrderObject(OrderDetails requestDTO, boolean isComplete) {
		System.out.println("Inside Obj Order");
		requestDTO.setOrderStatus(isComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED);
		return requestDTO;
	}

}
