package it.academy.controllers.threads;

import it.academy.controllers.Dump;
import it.academy.controllers.Scientist;
import it.academy.controllers.resouces.Part;
import it.academy.utils.Constants;
import it.academy.utils.Rnd;

import java.util.List;

public class Minion extends Thread {

	private final Dump dump;
	private int currentNight;
	private final Scientist linkedScientist;


	public Minion(Dump dump, Scientist linkedScientist,String name) {
		this.setName(name);
		this.dump = dump;
		this.currentNight = 1;//с этой ночи слуга начнёт брать детали
		this.linkedScientist = linkedScientist;
	}

	@Override
	public void run() {
		//System.out.println(Thread.currentThread().getName() + " start. Minion");
		while (currentNight <= Constants.COUNT_OF_NIGHTS) {

			int wantToTakeInNight = defineCount();
			List<Part> backPack = dump.pickUpAnyParts(currentNight, wantToTakeInNight);

			if (backPack != null) {
				currentNight++;
			} else {
				continue;
			}
			if (!backPack.isEmpty()) {
				linkedScientist.acceptParts(backPack);
				backPack.clear();
			}
		}
		//System.out.println(Thread.currentThread().getName() + " finish. Minion");
	}

	private int defineCount() {
		return Rnd.getRandValue(Constants.MAX_MINION_PARTS_PER_NIGHT,
				Constants.MIN_MINION_PARTS_PER_NIGHT);
	}
}
