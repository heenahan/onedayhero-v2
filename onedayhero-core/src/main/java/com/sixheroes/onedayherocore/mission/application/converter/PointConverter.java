package com.sixheroes.onedayherocore.mission.application.converter;

public final class PointConverter {

    private PointConverter() {

    }

    public static String pointToString(double x, double y) {
        return "POINT(" + y + " " + x + ")";
    }

    public static String[] convertPoint(String point) {
        return point.replace("POINT(", "")
                .replace(")", "")
                .split(" ");
    }
}
