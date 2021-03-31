package downfall.patches;

import automaton.AutomatonChar;
import champ.ChampChar;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.ui.buttons.ReturnToMenuButton;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.unlocks.*;
import guardian.patches.GuardianEnum;
import javassist.CtBehavior;
import slimebound.patches.SlimeboundEnum;
import theHexaghost.TheHexaghost;


@SpirePatch(
        clz = VictoryScreen.class,
        method = "update"
)
public class VictoryScreenUnlockPatch {

    @SpireInsertPatch(
            locator = Locator.class
    )

    public static SpireReturn Insert(VictoryScreen __instance) {
        if ((AbstractDungeon.unlocks.isEmpty()) || (Settings.isDemo)) {
            if ((Settings.isDemo) || (Settings.isDailyRun)) {
                CardCrawlGame.startOver();
            } else if (UnlockTracker.isCharacterLocked("Guardian") && (AbstractDungeon.player.chosenClass == SlimeboundEnum.SLIMEBOUND)) {
                AbstractDungeon.unlocks.add(new GuardianUnlock());
                AbstractDungeon.unlockScreen.open((AbstractUnlock) AbstractDungeon.unlocks.remove(0));
                ////SlimeboundMod.logger.info("Triggered Guardian Unlock screen!");
                return SpireReturn.Return(null);
            } else if ((UnlockTracker.isCharacterLocked("Hexaghost")) && (AbstractDungeon.player.chosenClass == GuardianEnum.GUARDIAN)) {
                AbstractDungeon.unlocks.add(new HexaghostUnlock());
                AbstractDungeon.unlockScreen.open((AbstractUnlock) AbstractDungeon.unlocks.remove(0));
                // //SlimeboundMod.logger.info("Triggered Hexaghost Unlock screen!");
                return SpireReturn.Return(null);
            } else if ((UnlockTracker.isCharacterLocked("Champ")) && (AbstractDungeon.player.chosenClass == TheHexaghost.Enums.THE_SPIRIT)) {
                AbstractDungeon.unlocks.add(new ChampUnlock());
                AbstractDungeon.unlockScreen.open((AbstractUnlock) AbstractDungeon.unlocks.remove(0));
                ////SlimeboundMod.logger.info("Triggered Champ Unlock screen!");
                return SpireReturn.Return(null);
            } else if ((UnlockTracker.isCharacterLocked("Automaton")) && (AbstractDungeon.player.chosenClass == ChampChar.Enums.THE_CHAMP)) {
                AbstractDungeon.unlocks.add(new AutomatonUnlock());
                AbstractDungeon.unlockScreen.open((AbstractUnlock) AbstractDungeon.unlocks.remove(0));
                ////SlimeboundMod.logger.info("Triggered Automaton Unlock screen!");
                return SpireReturn.Return(null);
            } else if ((UnlockTracker.isCharacterLocked("Snecko")) && (AbstractDungeon.player.chosenClass == AutomatonChar.Enums.THE_AUTOMATON)) {
                AbstractDungeon.unlocks.add(new SneckoUnlock());
                AbstractDungeon.unlockScreen.open((AbstractUnlock) AbstractDungeon.unlocks.remove(0));
                ////SlimeboundMod.logger.info("Triggered Snecko Unlock screen!");
                return SpireReturn.Return(null);
            }

        }
        return SpireReturn.Continue();
    }


    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ReturnToMenuButton.class, "hide");
            int[] lines = LineFinder.findAllInOrder(ctMethodToPatch, methodCallMatcher);
            return lines;
        }
    }
}