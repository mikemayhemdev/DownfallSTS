package guardian.patches;

        import basemod.ReflectionHacks;
        import com.badlogic.gdx.graphics.Color;
        import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
        import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
        import com.megacrit.cardcrawl.cards.AbstractCard;
        import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
        import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
        import guardian.GuardianMod;

@SpirePatch(clz= CardGlowBorder.class,method = SpirePatch.CONSTRUCTOR,
        paramtypez = {
                AbstractCard.class})
public class StasisGlowPatch {

    @SpirePostfixPatch
    public static void Postfix(CardGlowBorder obj, AbstractCard card) {

        if (card.hasTag(GuardianMod.STASISGLOW))
            ReflectionHacks.setPrivate(obj, AbstractGameEffect.class, "color", new Color(Color.GOLDENROD));

    }
}




