package rpg.rpgcore.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class ChanceHelper {
    private final static Random rand = new Random();

    public static int getRandInt(final int min, final int max) throws IllegalArgumentException {
        return rand.nextInt(max - min + 1) + min;
    }

    public static Double getRandDouble(final double min, final double max) throws IllegalArgumentException {
        return (rand.nextDouble() * (max - min)) + min;
    }

    public static Float getRandFloat(final float min, final float max) throws IllegalArgumentException {
        return (rand.nextFloat() * (max - min)) + min;
    }

    public static boolean getChance(final double chance) {
        return ((chance >= 100) || (chance >= ThreadLocalRandom.current().nextDouble(0D, 100D)));
    }
}
