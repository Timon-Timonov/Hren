package it.academy.utils;

import it.academy.controllers.resouces.Part;

import java.util.*;

public class RobotLaboratory {

	public static int createRobots(Map<Part, Integer> map) {
		int robotsAmount = map.values().stream()
				.min(Integer::compareTo)
				.orElse(0);
		map.keySet().forEach(k -> map.put(k,map.get(k) - robotsAmount));
		return robotsAmount;
	}
}
