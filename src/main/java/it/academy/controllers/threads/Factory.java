package it.academy.controllers.threads;

import it.academy.controllers.Dump;
import it.academy.controllers.resouces.Part;
import it.academy.utils.Constants;
import it.academy.utils.Rnd;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Factory extends Thread {


    private final Dump dump;
    private int currentNight;
    private List<Part> todayList = new ArrayList<>();

    public Factory(Dump dump, String name) {
        this.setName(name);
        this.dump = dump;
    }

    @Override
    public void run() {
        while (currentNight <= Constants.COUNT_OF_NIGHTS) {
            todayList = generateTodayList(defineCount());
            if (dump.acceptParts(todayList)) {
                currentNight++;
                System.out.println("Factory night: " + currentNight + " timestamp: " + LocalTime.now());
                waitNextNight();
            }
        }
    }

    private void waitNextNight() {
        try {
            Thread.sleep(Constants.NIGHT_LONG);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int defineCount() {
        return Rnd.getRandValue(Constants.MAX_FACTORY_PARTS_PER_NIGHT,
                Constants.MIN_FACTORY_PARTS_PER_NIGHT);
    }

    public static List<Part> generateTodayList(int count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> Part.values()[
                        Constants.RND.nextInt(Part.values().length)])
                .collect(Collectors.toList());
    }
}
