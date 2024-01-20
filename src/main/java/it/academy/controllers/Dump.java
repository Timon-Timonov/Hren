package it.academy.controllers;

import it.academy.controllers.resouces.Part;
import it.academy.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Dump {

	private List<Part> dumpList = new ArrayList<>();

	public void acceptParts(List<Part> list) {
		dumpList.addAll(list);
		System.out.println("Dump contains: " + dumpList);
	}

	public Part pickUpAnyPart() {
		return dumpList.isEmpty() ?
				null :
				dumpList.remove(Constants.RND.nextInt(dumpList.size()));
	}
}
