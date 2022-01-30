package com.example.service.steps;

import com.example.enums.WorkFlowStatus;

import reactor.core.publisher.Mono;

public interface WorkStep {
	
	WorkFlowStatus getStatus();
	Mono<Boolean> process();
	Mono<Boolean> revert();
}
