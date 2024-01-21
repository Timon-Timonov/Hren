package it.academy.utils;

import it.academy.controllers.resouces.Part;

import java.util.Map;

public class RobotLaboratory {

	public static int createRobots(Map<Part, Integer> map) {

		int countOfRobots = map.values().stream()
				.min(Integer::compareTo).orElse(0);

		map.keySet().forEach(part -> {
			int value = map.get(part);
			map.put(part, value - countOfRobots);
		});
		return countOfRobots;
	}
}
