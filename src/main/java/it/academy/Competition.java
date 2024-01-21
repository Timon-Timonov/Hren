package it.academy;

import it.academy.controllers.Dump;
import it.academy.controllers.Scientist;
import it.academy.utils.RobotLaboratory;
import it.academy.controllers.threads.Factory;
import it.academy.controllers.threads.Minion;
import it.academy.controllers.threads.Timer;
import it.academy.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Competition {

	private final List<Scientist> scientists = new ArrayList<>();
	private final List<Thread> threads = new ArrayList<>();

	private StringBuilder winners = new StringBuilder();
	private StringBuilder losers = new StringBuilder();

	public void start() {
		Dump dump = new Dump(Constants.DUMP);
		Factory factory = new Factory(dump, Constants.FACTORY);
		threads.add(factory);

		IntStream.range(0, Constants.COUNT_OF_SCIENTISTS).forEach(i -> {
			Scientist scientist = new Scientist(Constants.SCIENTIST + (i + 1));
			threads.add(new Minion(dump, scientist,Constants.MINION + (i + 1)));
			scientists.add(scientist);
		});

		threads.add(Timer.getInstance());

		threads.forEach(Thread::start);
		threads.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		findWinner(scientists);
		printResults();

	}

	private void findWinner(List<Scientist> list) {
		List<Integer> countOfRobots = list.stream()
				.map(scientist -> RobotLaboratory.createRobots(scientist.getStockMap()))
				.collect(Collectors.toList());

		int maxCount = countOfRobots.stream()
				.max(Integer::compareTo).orElse(0);

		int minCount = countOfRobots.stream()
				.min(Integer::compareTo).orElse(0);

		if (maxCount != 0) {
			if (maxCount == minCount) {
				winners.append("Game draw!\nAll are build ").append(maxCount).append(" robots.");
				losers = null;
			} else {
				for (int i = 0; i < countOfRobots.size(); i++) {
					int count = countOfRobots.get(i);
					if (count == maxCount) {
						winners.append(scientists.get(i).getName()).append(" build ").append(count).append(" robots\n");
					} else {
						losers.append(scientists.get(i).getName()).append(" build ").append(count).append(" robots\n");
					}
				}
			}
		} else {
			winners = null;
			losers.append("All are losers!\nNobody has build any robots.");
		}
	}

	private void printResults() {

		System.out.println();
		System.out.println("R_E_S_S_U_L_T_S");

		if (winners != null) {
			System.out.println();
			System.out.println("Winners: ");
			System.out.println(winners);
		}
		if (losers != null) {
			System.out.println();
			System.out.println("Losers: ");
			System.out.println(losers);
		}
	}
}

