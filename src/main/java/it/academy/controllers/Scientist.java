package it.academy.controllers;

import it.academy.controllers.resouces.Part;

import java.util.*;

public class Scientist {
	private Map<Part, Integer> stockMap;
	private String name;


	public Scientist(String name) {
		this.name=name;
		this.stockMap = new HashMap<>();
		Arrays.stream(Part.values()).forEach(part -> stockMap.put(part, 0));
	}

	public void acceptParts(List<Part> list) {

		list.stream()
				.filter(Objects::nonNull)
				.forEach(part -> {
					int count = stockMap.get(part) + 1;
					stockMap.put(part, count);
				});
	}

	public Map<Part, Integer> getStockMap() {
		return stockMap;
	}

	public String getName() {
		return name;
	}
}
