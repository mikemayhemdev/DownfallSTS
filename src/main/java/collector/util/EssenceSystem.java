package collector.util;

public class EssenceSystem {
    public static final int STARTING_ESSENCE = 5;

    private static int curEssence = STARTING_ESSENCE;

    public static void changeEssence(int amount) {
        curEssence += amount;
        //TODO: If positive, play vfx on top panel item
    }

    public static int essenceCount() {
        return curEssence;
    }

    public static void resetEssence() {
        curEssence = STARTING_ESSENCE;
    }

    public static void setEssence(int newTotal) {
        curEssence = newTotal;
    }
}