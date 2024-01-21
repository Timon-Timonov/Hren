package it.academy.controllers;

import it.academy.controllers.resouces.Part;
import it.academy.controllers.resouces.TimeHolder;
import it.academy.controllers.threads.Factory;
import it.academy.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Dump {

	private final List<Part> dumpList = new ArrayList<>();
	private final TimeHolder holder = TimeHolder.getInstance();
	private final String name;

	public Dump(String name) {
		this.name = name;
		this.dumpList.addAll(Factory.generateTodayList(Constants.START_COUNT_OF_DUMP_PARTS));
		System.out.println(name + "contain at start " + dumpList.size() + " parts: " + dumpList);
	}


	public List<Part> pickUpAnyParts(int minionNight, int wantToTake) {
		List<Part> newList = new ArrayList<>();

		for (int i = 0; i < wantToTake; i++) {
			Part part;

			synchronized (holder) {

				if (minionNight == holder.getCurrentNightNumber()) {
					part = dumpList.isEmpty() ?
							null :
							dumpList.remove(Constants.RND.nextInt(dumpList.size()));

				} else if (minionNight > holder.getCurrentNightNumber()) {
					try {
						holder.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return null;

				} else {
					System.out.println(Thread.currentThread().getName() + " Night_"
							+ minionNight + ". Pickup " + newList.size() + " parts " + newList);
					return newList;
				}
			}

			if (part != null) {
				newList.add(part);
			}
		}

		System.out.println(Thread.currentThread().getName() + " Night_"
				+ minionNight + ". Pickup " + newList.size() + " parts " + newList);

		try {
			synchronized (holder) {
				holder.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return newList;
	}


	public boolean acceptParts(int factoryNight, List<Part> list) {

		synchronized (holder) {
			int holderNight = holder.getCurrentNightNumber();

			if (factoryNight == holderNight) {
				dumpList.addAll(list);
				System.out.println(Thread.currentThread().getName() + " Night_" + holderNight + ". Throw_" + list.size() + " parts: " + list);
				System.out.println(name + " Night_" + holderNight + " contain " + dumpList.size() + " parts: " + dumpList);
				try {
					holder.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (factoryNight > holderNight) {
				try {
					holder.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				list.clear();
				return false;
			} else {
				list.clear();
				return true;
			}
		}
		list.clear();
		return true;
	}
}
