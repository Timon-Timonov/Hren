package it.academy.classes;

import it.academy.classes.models.Part;
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
		int size = dumpList.size();
		return size == 0 ?
				null :
				dumpList.remove(Constants.RND.nextInt(size));
	}
}
