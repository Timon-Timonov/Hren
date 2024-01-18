package it.academy.controllers.threads;

import it.academy.controllers.Dump;
import it.academy.controllers.Scientist;
import it.academy.controllers.resouces.Part;
import it.academy.controllers.resouces.TimeHolder;
import it.academy.utils.Constants;
import it.academy.utils.Rnd;

import java.util.ArrayList;
import java.util.List;

public class Minion extends Thread {

	private Dump dump;
	private int currentNight;
	private int wantToTakeInNight;
	private final Scientist linkedScientist;
	private final TimeHolder holder = TimeHolder.getInstance();
	private List<Part> backPack = new ArrayList<>();


	public Minion(Dump dump, Scientist linkedScientist) {
		this.dump = dump;
		this.currentNight = 1;//с этой ночи слуга начнёт брать детали(до того отчёт печатается с этим числом ночи - взял 0 деталей)
		this.wantToTakeInNight = defineCount();
		this.linkedScientist = linkedScientist;
	}

	@Override
	public void run() {
		//System.out.println(Thread.currentThread().getName() + " start. Minion");
		int alreadyTake = 0;

		synchronized (holder) {
			toWait();
		}

		while (currentNight <= Constants.COUNT_OF_NIGHTS) {

			synchronized (holder) {
				int holderNightNumber = holder.getCurrentNightNumber();

				if (currentNight <holderNightNumber) {
					currentNight = holderNightNumber;
					alreadyTake = 0;
					defineCount();
					continue;

				} else if (currentNight == holderNightNumber) {

					if (wantToTakeInNight > alreadyTake) {
						takePart(dump.pickUpAnyPart());
						alreadyTake++;
						continue;
					}
				}
				throwParts();
				toWait();
			}
		}
		//System.out.println(Thread.currentThread().getName() + " finish. Minion");
	}

	private void toWait() {
		try {
			holder.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	private int defineCount() {
		return Rnd.getRandValue(Constants.MAX_MINION_PARTS_PER_NIGHT,
				Constants.MIN_MINION_PARTS_PER_NIGHT);
	}

	private void takePart(Part part) {
		if (part != null) {
			backPack.add(part);
		}
	}

	private void throwParts() {
		System.out.println(linkedScientist.getName() + "__Minion. Night_"
				+ currentNight + ". Pickup " + backPack.size() + " parts " + backPack);
		linkedScientist.acceptParts(backPack);
		backPack.clear();
	}
}
