package slimebound.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import slimebound.SlimeboundMod;

@SpirePatch(clz = CardGlowBorder.class, method = SpirePatch.CONSTRUCTOR,
        paramtypez = {
                AbstractCard.class})
public class GoopGlowEffectPatch {

    @SpirePostfixPatch
    public static void Postfix(CardGlowBorder obj, AbstractCard card) {

        if (SlimeboundMod.goopGlow && card.hasTag(SlimeboundMod.GOOPEXPLOIT))
            ReflectionHacks.setPrivate(obj, AbstractGameEffect.class, "color", new Color(Color.PURPLE));

    }
}




