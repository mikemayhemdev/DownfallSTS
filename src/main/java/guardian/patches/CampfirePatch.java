package guardian.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;
import guardian.relics.PickAxe;
import guardian.ui.EnhanceBonfireOption;
import guardian.ui.FindGemsOption;

import java.util.ArrayList;

@SpirePatch(cls = "com.megacrit.cardcrawl.rooms.CampfireUI", method = "initializeButtons")
public class CampfirePatch {

    public static void Postfix(final Object meObj) {
        Boolean active = true;
        if (GuardianMod.getSocketableCards().size() == 0) {
            active = false;
        }
        if (GuardianMod.getGemCards().size() == 0) {
            active = false;
        }

        if (AbstractDungeon.player instanceof GuardianCharacter || active) {
            final CampfireUI campfire = (CampfireUI) meObj;
            try {
                final ArrayList<AbstractCampfireOption> campfireButtons = (ArrayList<AbstractCampfireOption>) ReflectionHacks.getPrivate((Object) campfire, (Class) CampfireUI.class, "buttons");


                GuardianMod.socketBonfireOption = new EnhanceBonfireOption(active);
                campfireButtons.add(GuardianMod.socketBonfireOption);
                float x = 950.f;
                float y = 990.0f - (270.0f * (float) ((campfireButtons.size() + 1) / 2));
                if (campfireButtons.size() % 2 == 0) {
                    x = 1110.0f;
                    campfireButtons.get(campfireButtons.size() - 2).setPosition(800.0f * Settings.scale, y * Settings.scale);
                }
                campfireButtons.get(campfireButtons.size() - 1).setPosition(x * Settings.scale, y * Settings.scale);
            } catch (SecurityException | IllegalArgumentException ex2) {
                //final RuntimeException ex;
                //final RuntimeException e = ex;
                //e.printStackTrace();
            }

        }
        if (AbstractDungeon.player.hasRelic(PickAxe.ID)) {
            final CampfireUI campfire = (CampfireUI) meObj;
            try {
                final ArrayList<AbstractCampfireOption> campfireButtons = (ArrayList<AbstractCampfireOption>) ReflectionHacks.getPrivate((Object) campfire, (Class) CampfireUI.class, "buttons");

                boolean relicActive;
                if (AbstractDungeon.player.getRelic(PickAxe.ID).counter == -2) {
                    relicActive = false;
                } else {
                    relicActive = true;
                }

                campfireButtons.add(new FindGemsOption(relicActive));
                float x = 950.f;
                float y = 990.0f - (270.0f * (float) ((campfireButtons.size() + 1) / 2));
                if (campfireButtons.size() % 2 == 0) {
                    x = 1110.0f;
                    campfireButtons.get(campfireButtons.size() - 2).setPosition(800.0f * Settings.scale, y * Settings.scale);
                }
                campfireButtons.get(campfireButtons.size() - 1).setPosition(x * Settings.scale, y * Settings.scale);
            } catch (SecurityException | IllegalArgumentException ex2) {
                //final RuntimeException ex;
                //final RuntimeException e = ex;
                //e.printStackTrace();
            }
        }

    }
}

