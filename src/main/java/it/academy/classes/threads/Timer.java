package it.academy.classes.threads;

import it.academy.classes.models.TimeHolder;
import it.academy.utils.Constants;

public class Timer extends Thread {

	private static Timer timer = null;
	private final TimeHolder holder = TimeHolder.getInstance();

	private Timer() {
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
		for (int i = 0; i <= Constants.COUNT_OF_NIGHTS + 1; i++) {

			synchronized (holder) {
				holder.setCurrentNightNumber(i);
				holder.notifyAll();
				System.out.println(Thread.currentThread().getName() + "__Timer.  Night_" + i);
			}
			toSleep();
		}
		//System.out.println(Thread.currentThread().getName() + " finish. Timer");
	}

	private void toSleep() {
		try {
			Thread.sleep(Constants.NIGHT_LONG);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
