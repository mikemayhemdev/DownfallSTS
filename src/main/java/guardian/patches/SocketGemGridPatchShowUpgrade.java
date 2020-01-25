/*
package guardian.patches;


import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import guardian.GuardianMod;
import guardian.cards.AbstractGuardianCard;

import java.util.Iterator;

@SpirePatch(clz= GridCardSelectScreen.class, method="open", paramtypez = {CardGroup.class, int.class, String.class, boolean.class, boolean.class, boolean.class, boolean.class})
public class SocketGemGridPatchShowUpgrade {
    @SpireInsertPatch(
            rloc = 182
    )

    @SpirePrefixPatch
    public static void Insert(GridCardSelectScreen obj, CardGroup group, int numCards, String tipMsg, boolean forUpgrade, boolean forTransform, boolean canCancel, boolean forPurge) {

                            AbstractCard hack_hoveredCard = (AbstractCard)ReflectionHacks.getPrivate(obj, GridCardSelectScreen.class,"hoveredCard");

                            if (GuardianMod.gridScreenForSockets) {
                                hack_hoveredCard.untip();
                                obj.confirmScreenUp = true;
                                obj.upgradePreviewCard = hack_hoveredCard.makeStatEquivalentCopy();
                                ((AbstractGuardianCard)obj.upgradePreviewCard).addGemToSocket(GuardianMod.currSocketGemEffect.gemChosen);
                                obj.upgradePreviewCard.drawScale = 0.875F;
                                hack_hoveredCard.stopGlowing();
                                obj.selectedCards.clear();
                                AbstractDungeon.overlayMenu.cancelButton.show(obj.TEXT[1]);
                                obj.confirmButton.show();
                                obj.confirmButton.isDisabled = false;
                                return;
                            }
    }

}
 */