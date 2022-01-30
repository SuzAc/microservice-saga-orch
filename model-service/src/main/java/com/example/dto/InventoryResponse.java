package com.example.dto;

import com.example.enums.InventoryStatus;

public class InventoryResponse {
	String id;
	InventoryStatus status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public InventoryStatus getStatus() {
		return status;
	}
	public void setStatus(InventoryStatus status) {
		this.status = status;
	}
}
