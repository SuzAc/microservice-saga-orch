package com.example.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.enums.OrderStatus;
/**
 * @author sujit.sahoo
 */
public class OrderDetails implements Serializable{

	private static final long serialVersionUID = -7618052478924225481L;
	private UUID id;
	private String userId;
	private String productId;
	private Double price;
	private OrderStatus orderStatus;
	
	
	public OrderDetails() {
		super();
		this.id = UUID.randomUUID();
	}


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public OrderStatus getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}
