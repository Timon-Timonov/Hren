package it.academy.classes.threads;

import it.academy.classes.Dump;
import it.academy.classes.models.Part;
import it.academy.classes.models.TimeHolder;
import it.academy.utils.Constants;
import it.academy.utils.Rnd;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Factory extends Thread {

	private String name;
	private Dump dump;
	private int currentNight;
	private final TimeHolder holder = TimeHolder.getInstance();

	public Factory(Dump dump, String name) {
		this.name = name;
		this.dump = dump;
		this.currentNight = 1;//1(не) или 0(должна) в зависимости от того должна ли фабрика в первую ночь добавлять новые детали к уже имеющимся на свалке 20-ти частям

	}

	@Override
	public void run() {
		//System.out.println(Thread.currentThread().getName() + " start. Factory");
		throwParts(generateTodayList(Constants.START_COUNT_OF_DUMP_PARTS));//напечатается отчёт с номером ночи взятым из конструктора, но само добавление происходит ДО начала соревнования
		synchronized (holder) {
			toWait();
		}

		while (currentNight < Constants.COUNT_OF_NIGHTS) {
			synchronized (holder) {

				int temp = holder.getCurrentNightNumber();
				if (currentNight < temp) {
					currentNight = temp;
					int count = defineCount();
					throwParts(generateTodayList(count));
				}
				toWait();
			}
		}
		//System.out.println(Thread.currentThread().getName() + " finish. Factory");
	}

	private int defineCount() {
		return Rnd.getRandValue(Constants.MAX_FACTORY_PARTS_PER_NIGHT,
				Constants.MIN_FACTORY_PARTS_PER_NIGHT);
	}

	private List<Part> generateTodayList(int count) {
		List<Part> list = new ArrayList<>();

		IntStream.range(0, count)
				.forEach(i -> list.add(
						Part.values()[
								Constants.RND.nextInt(
										Part.values().length)]));
		return list;
	}

	private void throwParts(List<Part> list) {
		System.out.println(name + "_Factory. Night_" + currentNight + ". Throw  " + list.size() + " parts " + list);
		dump.acceptParts(list);
	}

	private void toWait() {
		try {
			holder.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
