package collector.util;

public class NewReserves {
    private static int curReserves = 0;

    public static void addReserves(int amount) {
        curReserves += amount;
    }

    public static void spendReserves(int amount) {
        curReserves += amount;
    }

    public static int reserveCount() {
        return curReserves;
    }

    public static void resetReserves() {
        curReserves = 0;
    }
}