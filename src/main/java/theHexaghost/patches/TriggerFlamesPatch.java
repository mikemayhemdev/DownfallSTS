package theHexaghost.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import theHexaghost.ghostflames.BolsteringGhostflame;
import theHexaghost.ghostflames.CrushingGhostflame;
import theHexaghost.ghostflames.InfernoGhostflame;
import theHexaghost.ghostflames.SearingGhostflame;

import static theHexaghost.GhostflameHelper.activeGhostFlame;
import static theHexaghost.HexaMod.renderFlames;

@SpirePatch(
        clz = CardGroup.class,
        method = "triggerOnOtherCardPlayed"
)
public class TriggerFlamesPatch {
    public static void Prefix(CardGroup __instance, AbstractCard abstractCard) {
        if (!activeGhostFlame.charged && renderFlames)
            if (activeGhostFlame instanceof SearingGhostflame && abstractCard.type == AbstractCard.CardType.ATTACK) {
                ((SearingGhostflame) activeGhostFlame).attacksPlayedThisTurn++;
                if (((SearingGhostflame) activeGhostFlame).attacksPlayedThisTurn == 2) {
                    activeGhostFlame.charge();
                } else {
                    activeGhostFlame.flash();
                }
            } else if (activeGhostFlame instanceof CrushingGhostflame && abstractCard.type == AbstractCard.CardType.SKILL) {
                ((CrushingGhostflame) activeGhostFlame).skillsPlayedThisTurn++;
                if (((CrushingGhostflame) activeGhostFlame).skillsPlayedThisTurn == 2) {
                    activeGhostFlame.charge();
                } else {
                    activeGhostFlame.flash();
                }
            } else if (activeGhostFlame instanceof BolsteringGhostflame && abstractCard.type == AbstractCard.CardType.POWER) {
                activeGhostFlame.charge();
            } else if (activeGhostFlame instanceof InfernoGhostflame) {
                int x = abstractCard.costForTurn;
                if (abstractCard.freeToPlayOnce) x = 0;
                else if (abstractCard.cost == -1) x = abstractCard.energyOnUse;
                ((InfernoGhostflame) activeGhostFlame).energySpentThisTurn += x;
                if (((InfernoGhostflame) activeGhostFlame).energySpentThisTurn >= 3) {
                    activeGhostFlame.charge();
                } else {
                    activeGhostFlame.flash();
                }
            }
    }
}