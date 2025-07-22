package awakenedOne.patches;

import awakenedOne.AwakenedTextHelper;
import awakenedOne.cards.AbstractAwakenedCard;
import awakenedOne.powers.InResponsePower;
import champ.powers.FalseCounterPower;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static awakenedOne.AwakenedOneMod.ACTIVECHANT;
import static awakenedOne.AwakenedOneMod.UP_NEXT;
import static awakenedOne.util.Wiz.atb;

@SpirePatch(clz = StSLib.class, method = "onCreateCard", paramtypez = {AbstractCard.class})
public class OnCreateCardSubscriber {
    public static int CardsCreatedThisCombat = 0;
    public static int CardsCreatedThisTurn = 0;
    @SpirePostfixPatch
    public static void onCreateCard(AbstractCard c) {
        if (!AbstractDungeon.actionManager.turnHasEnded) {
            if (AbstractDungeon.player.hasPower(InResponsePower.POWER_ID)) {
                AbstractDungeon.player.getPower(InResponsePower.POWER_ID).onSpecificTrigger();
            }
        }
        if (c.hasTag(ACTIVECHANT)) {
            AwakenedTextHelper.colorCombos((AbstractAwakenedCard)c, false);
            c.initializeDescription();
        }
        CardsCreatedThisCombat++;
        CardsCreatedThisTurn++;
    }
}