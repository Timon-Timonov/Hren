package it.academy.controllers.resouces;

public class TimeHolder {

	private static TimeHolder timeHolder = null;
	private int currentNightNumber = 0;

	private TimeHolder() {
	}

	public static synchronized TimeHolder getInstance() {
		if (timeHolder == null) {
			timeHolder = new TimeHolder();
		}
		return timeHolder;
	}

	public int getCurrentNightNumber() {
		return currentNightNumber;
	}

	public void setCurrentNightNumber(int currentNightNumber) {
		this.currentNightNumber = currentNightNumber;
	}
}
