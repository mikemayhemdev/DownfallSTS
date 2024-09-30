package expansioncontent.util;

public class DownfallAchievementVariables {
    public static boolean threeShapesFought = false;
    public static boolean functionCreated = false;
    public static int slimesEvoked = 0;
    public static int gemsSocketed = 0;
    public static int cardsReturnedFromStasis = 0;
    public static int fullInfernoIgnitions = 0;
    public static int councilStrikesRemoved = 0;
    public static int councilDefendsRemoved = 0;
    public static int enteredUltimateStance = 0;
    public static int statusesMutated = 0;
    public static int bountiesCollectedTemp = 0;
    public static int bountiesCollectedPerm = 0;

    public static void resetRunAchievementVariables() {
        bountiesCollectedPerm = 0;
    }

    public static void resetBattleAchievementVariables() {
        slimesEvoked = 0;
        functionCreated = false;
        bountiesCollectedTemp = 0;
        fullInfernoIgnitions = 0;
    }

    public static void resetTurnAchievementVariables() {
        cardsReturnedFromStasis = 0;
        enteredUltimateStance = 0;
        statusesMutated = 0;
    }

    public static void resetFloorAchievementVariables() {
        threeShapesFought = false;
        gemsSocketed = 0;
        councilStrikesRemoved = 0;
        councilDefendsRemoved = 0;
    }

}
