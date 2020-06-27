package downfall.patches.ui.campfire;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import downfall.relics.GremlinWheel;
import downfall.ui.campfire.WheelSpinButton;
import javassist.CtBehavior;

import java.util.ArrayList;

public class AddWheelSpinButtonPatch {
    @SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
    public static class AddKeys {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(CampfireUI __instance, ArrayList<AbstractCampfireOption> ___buttons) {
            if (AbstractDungeon.player.hasRelic(GremlinWheel.ID)) {
                boolean relicActive;
                relicActive = AbstractDungeon.player.getRelic(GremlinWheel.ID).counter != 0;

                GremlinWheel gw = (GremlinWheel)AbstractDungeon.player.getRelic(GremlinWheel.ID);
                boolean justUsed = gw.justFailed;

                if (relicActive) ___buttons.add(new WheelSpinButton(!justUsed));
            }
        }
    }

    public static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}
