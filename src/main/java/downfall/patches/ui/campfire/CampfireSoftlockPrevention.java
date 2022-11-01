package downfall.patches.ui.campfire;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;


@SpirePatch(clz = CampfireUI.class, method = "update")
public class CampfireSoftlockPrevention {

    @SpirePostfixPatch
    public static void Postfix(CampfireUI __instance) {
        if (!__instance.somethingSelected) {
            boolean softlocked = true;
            ArrayList<AbstractCampfireOption> rrButtons = ReflectionHacks.getPrivate(__instance, CampfireUI.class, "buttons");
            for (AbstractCampfireOption b : rrButtons) {
                if (b.usable) {
                    softlocked = false;
                    break;
                }
            }
            if (softlocked) {
                __instance.somethingSelected = true;
                __instance.touchOption = null;
                __instance.confirmButton.show();
                __instance.confirmButton.isDisabled = false;

                AbstractDungeon.overlayMenu.proceedButton.show();
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            }
        }
    }
}

