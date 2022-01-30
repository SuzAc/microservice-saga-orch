package com.example.repo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

@Repository
public class InventoryStaticRepository {
	public static Map<String, Integer> repoInv = new HashMap<>();

	@PostConstruct
	static void feedData() {
		repoInv.put("A", 3);
		repoInv.put("B", 3);
		repoInv.put("C", 3);
		repoInv.put("D", 3);

	}

	public synchronized boolean deductCount(String productId) {
		System.out.println(productId);
		int invCount = repoInv.get(productId);
		if (invCount > 0) {
			repoInv.put(productId, invCount - 1);
			return true;
		}
		return false;
	}

	public synchronized void addCount(String productId) {
		repoInv.put(productId, repoInv.get(productId) + 1);
	}
}
