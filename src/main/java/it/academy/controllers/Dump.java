package it.academy.controllers;

import it.academy.controllers.resouces.Part;
import it.academy.controllers.threads.Factory;
import it.academy.utils.Constants;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Dump {

    private final List<Part> dumpList = new ArrayList<>();
    private final String name;
    private int currentNight;

    public Dump(String name) {
        this.name = name;
        this.dumpList.addAll(Factory.generateTodayList(Constants.START_COUNT_OF_DUMP_PARTS));
        System.out.println(name + "contain at start " + dumpList.size() + " parts: " + dumpList);
    }

    public synchronized List<Part> pickUpAnyParts(int minionNight, int wantToTake) {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Part> newList = new ArrayList<>();

        for (int i = 0; i < wantToTake; i++) {
            Part part = dumpList.isEmpty() ?
                    null :
                    dumpList.remove(Constants.RND.nextInt(dumpList.size()));
            if (part != null) {
                newList.add(part);
            }
        }

        System.out.println(Thread.currentThread().getName() + " Night_"
                + minionNight + ". Pickup " + newList.size() + " parts " + newList + " timestamp: " + LocalTime.now());

        return newList;
    }

    public synchronized boolean acceptParts(List<Part> list) {
        currentNight++;
        System.out.println(Thread.currentThread().getName() + " Night_" + currentNight + ". Throw_" + list.size() + " parts: " + list
                + " " + name + " size: " + dumpList.size() + " " + name + ":\n" + dumpList + " timestamp: " + LocalTime.now());
        notifyAll();
        return dumpList.addAll(list);
    }
}
