package rpg.rpgcore.utils;

public class DoubleUtils {
    private DoubleUtils() {
        throw new ExceptionInInitializerError("Cannot initialize utility class!");
    }

    public static double round(double valueToRound, int numberOfDecimalPlaces) {
        double multipicationFactor = Math.pow(10.0D, (double)numberOfDecimalPlaces);
        double interestedInZeroDPs = valueToRound * multipicationFactor;
        return (double)Math.round(interestedInZeroDPs) / multipicationFactor;
    }
}
