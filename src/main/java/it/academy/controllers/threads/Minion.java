package it.academy.controllers.threads;

import it.academy.controllers.Dump;
import it.academy.controllers.Scientist;
import it.academy.controllers.resouces.Part;
import it.academy.utils.Constants;
import it.academy.utils.Rnd;

import java.time.LocalTime;
import java.util.List;

public class Minion extends Thread {

    private final Dump dump;
    private int currentNight;
    private final Scientist linkedScientist;


    public Minion(Dump dump, Scientist linkedScientist, String name) {
        this.setName(name);
        this.dump = dump;
        this.currentNight = 1;
        this.linkedScientist = linkedScientist;
    }

    @Override
    public void run() {
        while (currentNight <= Constants.COUNT_OF_NIGHTS) {
            int wantToTakeInNight = defineCount();
            List<Part> backPack = dump.pickUpAnyParts(currentNight, wantToTakeInNight);
            System.out.println("Minion night: " + currentNight + " timestamp: " + LocalTime.now());
            currentNight++;
            if (!backPack.isEmpty()) {
                linkedScientist.acceptParts(backPack);
                backPack.clear();
            }
        }
    }

    private int defineCount() {
        return Rnd.getRandValue(Constants.MAX_MINION_PARTS_PER_NIGHT,
                Constants.MIN_MINION_PARTS_PER_NIGHT);
    }
}
