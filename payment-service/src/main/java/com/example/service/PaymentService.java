package com.example.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.dto.OrderDetails;
import com.example.dto.PaymentResponse;
import com.example.enums.PaymentStatus;
import com.example.repo.PaymentStaticRepository;

import reactor.core.publisher.Mono;

@Service
public class PaymentService {

	private static Logger log = LoggerFactory.getLogger(PaymentService.class);

	@Autowired
	private PaymentStaticRepository paymentStaticRepository;

	public Mono<ServerResponse> deduct(ServerRequest serverRequest) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("payment processing starting");
		return serverRequest.bodyToMono(OrderDetails.class).log().flatMap(req -> {
			PaymentResponse paymentResponse = new PaymentResponse();
			paymentResponse.setStatus(
					paymentStaticRepository.deductBalance(req.getUserId(), req.getPrice()) ? PaymentStatus.SUCCESS
							: PaymentStatus.FAILURE);
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(paymentResponse);
		});
	}

	public Mono<ServerResponse> credit(ServerRequest serverRequest) {
		log.info("payment refund starting");
		return serverRequest.bodyToMono(OrderDetails.class).flatMap(req -> {
			PaymentResponse paymentResponse = new PaymentResponse();
			paymentStaticRepository.addBalance(req.getUserId(), req.getPrice());
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(paymentResponse);
		});

	}

	public Mono<ServerResponse> findBalance(ServerRequest serverRequest) {
		serverRequest.bodyToMono(OrderDetails.class).subscribe(a -> System.out.println(a.getProductId()));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(paymentStaticRepository.repoBal);
	}

}
