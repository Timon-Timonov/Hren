package it.academy.utils;

import it.academy.controllers.resouces.Part;

import java.util.*;

public class RobotLaboratory {

	public static int createRobots(Map<Part, Integer> map) {
		return map.values().stream()
				.min(Integer::compareTo).orElse(0);
	}
}
