package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import guardian.GuardianMod;

@SpirePatch(clz = GridCardSelectScreen.class, method = "open", paramtypez = {CardGroup.class, int.class, String.class, boolean.class, boolean.class, boolean.class, boolean.class})
public class SocketGemGridPatchCancelButton {

    @SpirePrefixPatch
    public static void Prefix(GridCardSelectScreen obj, CardGroup group, int numCards, String tipMsg, boolean forUpgrade, boolean forTransform, boolean canCancel, boolean forPurge) {
        if ((GuardianMod.gridScreenForGems || GuardianMod.gridScreenForSockets) && canCancel) {
            AbstractDungeon.overlayMenu.cancelButton.show(obj.TEXT[1]);
        }
    }

}