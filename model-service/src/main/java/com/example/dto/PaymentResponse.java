package com.example.dto;

import com.example.enums.PaymentStatus;

public class PaymentResponse {
	String id;
	PaymentStatus status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PaymentStatus getStatus() {
		return status;
	}
	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
}
