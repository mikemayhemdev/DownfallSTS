package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;
import timeEater.ClockHelper;
import timeEater.actions.TickAction;

import static theHexaghost.GhostflameHelper.activeGhostFlame;
import static theHexaghost.HexaMod.renderFlames;

@SpirePatch(
        clz = CardGroup.class,
        method = "triggerOnOtherCardPlayed"
)
public class GlobalOnCardUsePatch {
    public static void Prefix(CardGroup __instance, AbstractCard abstractCard) {
        if (!activeGhostFlame.charged && renderFlames && activeGhostFlame.advanceOnCardUse)
            activeGhostFlame.advanceTrigger(abstractCard);
        if (abstractCard.hasTag(expansionContentMod.STUDY)) {
            downfallMod.playedBossCardThisTurn = true;
        }
        if (ClockHelper.active) {
            AbstractDungeon.actionManager.addToBottom(new TickAction());
        }
    }
}