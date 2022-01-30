package com.example.repo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

@Repository
public class PaymentStaticRepository {
	public static Map<String, Double> repoBal = new HashMap<>();

	@PostConstruct
	static void feedData() {
		repoBal.put("01", 200.0);
		repoBal.put("02", 250.0);
		repoBal.put("03", 260.0);
		repoBal.put("04", 270.0);

	}

	public synchronized boolean deductBalance(String userId, double amount) {
		double balance = repoBal.get(userId) - amount;
		if (balance > 0) {
			repoBal.put(userId, repoBal.get(userId) - amount);
			return true;
		}
		return false;
	}

	public synchronized void addBalance(String userId, double amount) {
		repoBal.put(userId, repoBal.get(userId) + amount);
	}

}
