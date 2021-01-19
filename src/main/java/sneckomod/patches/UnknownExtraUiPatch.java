package sneckomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sneckomod.cards.unknowns.AbstractUnknownCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class UnknownExtraUiPatch {
    public static SpireField<AbstractUnknownCard> parentCard = new SpireField<>(() -> null);
}