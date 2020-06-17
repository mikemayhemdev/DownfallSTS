package theHexaghost.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import theHexaghost.cards.seals.AbstractSealCard;

@SpirePatch(
        clz = SingleCardViewPopup.class,
        method = "renderUpgradeViewToggle"
)
public class NoMoreUpgradePopup {
    public static SpireReturn Prefix(SingleCardViewPopup __instance, SpriteBatch sb) {
        if ((AbstractCard) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card") instanceof AbstractSealCard) {
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}