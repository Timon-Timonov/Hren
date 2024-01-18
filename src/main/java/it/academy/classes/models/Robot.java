package it.academy.classes.models;

import java.util.*;

public class Robot {

	public static int createRobots(Map<Part, Integer> map) {
		return map.values().stream()
				.min(Integer::compareTo).orElse(0);
	}
}
