package com.test.springboot.common.util;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class FormatUtil {

	public static String formatDoubleNormal(Double d) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setRoundingMode(RoundingMode.HALF_UP);
		return nf.format(d);
	}
	
	public static double formatDoubleDigit(Double d) {
		return (double)Math.round(d * 100) / 100;
	}
}
