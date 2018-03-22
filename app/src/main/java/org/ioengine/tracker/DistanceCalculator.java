
package org.ioengine.tracker;

public final class DistanceCalculator {

    private static final double EQUATORIAL_EARTH_RADIUS = 6378.1370;
    private static final double DEG_TO_RAD = Math.PI / 180;

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double dlong = (lon2 - lon1) * DEG_TO_RAD;
        double dlat = (lat2 - lat1) * DEG_TO_RAD;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1 * DEG_TO_RAD) * Math.cos(lat2 * DEG_TO_RAD) * Math.pow(Math.sin(dlong / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = EQUATORIAL_EARTH_RADIUS * c;
        return d * 1000;
    }

}
