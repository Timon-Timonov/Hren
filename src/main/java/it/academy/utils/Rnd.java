package it.academy.utils;

public class Rnd {

	public static int getRandValue(int a, int b) {
		int min = Math.min(a, b);
		int max = min == a ?
				b : a;
		return (Constants.RND.nextInt(max - min + 1) + min);
	}
}
