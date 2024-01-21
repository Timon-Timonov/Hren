package it.academy.controllers.threads;

import it.academy.controllers.resouces.TimeHolder;
import it.academy.utils.Constants;

public class Timer extends Thread {

	private static Timer timer = null;
	private final TimeHolder holder = TimeHolder.getInstance();

	private Timer() {
		this.setName(Constants.TIMER);
	}

	public static synchronized Timer getInstance() {
		if (timer == null) {
			timer = new Timer();
		}
		return timer;
	}

	@Override
	public void run() {
		//System.out.println(Thread.currentThread().getName() + " start. Timer");
		for (int i = 1; i <= Constants.COUNT_OF_NIGHTS + 1; i++) {

			synchronized (holder) {
				holder.setCurrentNightNumber(i);
				holder.notifyAll();
				System.out.println(Thread.currentThread().getName() + " Night_" + i);
			}
			try {
				Thread.sleep(Constants.NIGHT_LONG);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//System.out.println(Thread.currentThread().getName() + " finish. Timer");
	}
}
