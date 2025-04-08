package org.example;

public class CalculateUnits {

    public static float convert(String category, String fromUnit, String toUnit, float value) {
        if (category.equals("time")) {
            return convertTime(fromUnit, toUnit, value);
        } else if (category.equals("distance")) {
            return convertDistance(fromUnit, toUnit, value);
        } else if (category.equals("speed")) {
            return convertSpeed(fromUnit, toUnit, value);
        } else if (category.equals("mass")) {
            return convertMass(fromUnit, toUnit, value);
        } else if (category.equals("area")) {
            return convertArea(fromUnit, toUnit, value);
        } else if (category.equals("volume")) {
            return convertVolume(fromUnit, toUnit, value);
        } else if (category.equals("pressure")) {
            return convertPressure(fromUnit, toUnit, value);
        } else if (category.equals("temperature")) {
            return convertTemperature(fromUnit, toUnit, value);
        } else if (category.equals("energy")) {
            return convertEnergy(fromUnit, toUnit, value);
        }
        return value; // If no conversion category matches, return the input value
    }

    private static float convertTime(String from, String to, float value) {
        float seconds = switch (from) {
            case "seconds" -> value;
            case "minutes" -> value * 60;
            case "hours" -> value * 3600;
            default -> value;
        };

        return switch (to) {
            case "seconds" -> seconds;
            case "minutes" -> seconds / 60;
            case "hours" -> seconds / 3600;
            default -> value;
        };
    }

    private static float convertDistance(String from, String to, float value) {
        float meters = switch (from) {
            case "meters" -> value;
            case "kilometers" -> value * 1000;
            case "miles" -> value * 1609.34f;
            default -> value;
        };

        return switch (to) {
            case "meters" -> meters;
            case "kilometers" -> meters / 1000;
            case "miles" -> meters / 1609.34f;
            default -> value;
        };
    }

    private static float convertSpeed(String from, String to, float value) {
        float mps = switch (from) {
            case "m/s" -> value;
            case "km/h" -> value / 3.6f;
            case "mph" -> value * 0.44704f;
            default -> value;
        };

        return switch (to) {
            case "m/s" -> mps;
            case "km/h" -> mps * 3.6f;
            case "mph" -> mps / 0.44704f;
            default -> value;
        };
    }

    private static float convertMass(String from, String to, float value) {
        float grams = switch (from) {
            case "grams" -> value;
            case "kilograms" -> value * 1000;
            case "pounds" -> value * 453.592f;
            default -> value;
        };

        return switch (to) {
            case "grams" -> grams;
            case "kilograms" -> grams / 1000;
            case "pounds" -> grams / 453.592f;
            default -> value;
        };
    }

    private static float convertArea(String from, String to, float value) {
        float sqm = switch (from) {
            case "sq.meters" -> value;
            case "sq.kilometers" -> value * 1_000_000;
            case "sq.feet" -> value * 0.092903f;
            default -> value;
        };

        return switch (to) {
            case "sq.meters" -> sqm;
            case "sq.kilometers" -> sqm / 1_000_000;
            case "sq.feet" -> sqm / 0.092903f;
            default -> value;
        };
    }

    private static float convertVolume(String from, String to, float value) {
        float liters = switch (from) {
            case "liters" -> value;
            case "milliliters" -> value / 1000;
            case "gallons" -> value * 3.78541f;
            default -> value;
        };

        return switch (to) {
            case "liters" -> liters;
            case "milliliters" -> liters * 1000;
            case "gallons" -> liters / 3.78541f;
            default -> value;
        };
    }

    private static float convertPressure(String from, String to, float value) {
        float pascals = switch (from) {
            case "Pascals" -> value;
            case "bars" -> value * 100_000;
            case "psi" -> value * 6894.76f;
            default -> value;
        };

        return switch (to) {
            case "Pascals" -> pascals;
            case "bars" -> pascals / 100_000;
            case "psi" -> pascals / 6894.76f;
            default -> value;
        };
    }

    private static float convertTemperature(String from, String to, float value) {
        if (from.equals(to)) return value;
        if (from.equals("Celsius") && to.equals("Fahrenheit")) return (value * 9/5) + 32;
        if (from.equals("Celsius") && to.equals("Kelvin")) return value + 273.15f;
        if (from.equals("Fahrenheit") && to.equals("Celsius")) return (value - 32) * 5/9;
        if (from.equals("Fahrenheit") && to.equals("Kelvin")) return ((value - 32) * 5/9) + 273.15f;
        if (from.equals("Kelvin") && to.equals("Celsius")) return value - 273.15f;
        if (from.equals("Kelvin") && to.equals("Fahrenheit")) return ((value - 273.15f) * 9/5) + 32;
        return value;
    }

    private static float convertEnergy(String from, String to, float value) {
        float joules = switch (from) {
            case "Joules" -> value;
            case "Calories" -> value * 4.184f;
            case "Watt-hours" -> value * 3600;
            default -> value;
        };

        return switch (to) {
            case "Joules" -> joules;
            case "Calories" -> joules / 4.184f;
            case "Watt-hours" -> joules / 3600;
            default -> value;
        };
    }
}

