package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;
import guardian.relics.PickAxe;
import guardian.ui.EnhanceBonfireOption;
import guardian.ui.FindGemsOption;
import javassist.CtBehavior;
import sneckomod.TheSnecko;
import sneckomod.relics.SneckoSoul;
import sneckomod.relics.SuperSneckoSoul;
import sneckomod.ui.LockInCampfireOption;

import java.util.ArrayList;

public class AddEnhanceButtonPatch {
    @SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
    public static class AddKeys {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(CampfireUI __instance, ArrayList<AbstractCampfireOption> ___buttons) {
            Boolean active = true;
            if (GuardianMod.getSocketableCards().size() == 0) {
                active = false;
            }
            if (GuardianMod.getGemCards().size() == 0) {
                active = false;
            }
            if (AbstractDungeon.player instanceof GuardianCharacter || active) {
                GuardianMod.socketBonfireOption = new EnhanceBonfireOption(active);
                ___buttons.add(GuardianMod.socketBonfireOption);
            }
            if (AbstractDungeon.player.hasRelic(PickAxe.ID)) {
                boolean relicActive;
                relicActive = AbstractDungeon.player.getRelic(PickAxe.ID).counter != -2;
                ___buttons.add(new FindGemsOption(relicActive));
            }


            if (AbstractDungeon.player.hasRelic(SuperSneckoSoul.ID) || AbstractDungeon.player.hasRelic(SneckoSoul.ID)) {
                ___buttons.add(new LockInCampfireOption());
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
