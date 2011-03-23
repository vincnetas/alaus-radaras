package alaus.radaras.utils;

import android.location.Location;

public class DistanceCalculator {

	private static double m1 = 111132.92; // latitude calculation term 1
	private static double m2 = -559.82; // latitude calculation term 2
	private static double m3 = 1.175; // latitude calculation term 3
	private static double m4 = -0.0023; // latitude calculation term 4
	private static double p1 = 111412.84; // longitude calculation term 1
	private static double p2 = -93.5; // longitude calculation term 2
	private static double p3 = 0.118; // longitude calculation term 3

	private static double deg2rad(double deg) {
		double conv_factor = (2.0 * Math.PI) / 360.0;
		return (deg * conv_factor);
	}

	public static Bounds getBounds(Location location, double maxDistance) {
		double lat = deg2rad(location.getLatitude());

		double latlen = m1 + (m2 * Math.cos(2 * lat)) + (m3 * Math.cos(4 * lat)) + (m4 * Math.cos(6 * lat));
		double longlen = (p1 * Math.cos(lat)) + (p2 * Math.cos(3 * lat)) + (p3 * Math.cos(5 * lat));

		double latDiff = maxDistance / latlen;
		double longDiff = maxDistance / longlen;

		Bounds result = new Bounds();

		result.setMaxLatitude(location.getLatitude() + latDiff);
		result.setMinLatitude(location.getLatitude() - latDiff);
		result.setMaxLongitude(location.getLongitude() + longDiff);
		result.setMinLongitude(location.getLongitude() - longDiff);

		return result;
	}
}
