package champ.util;

import basemod.abstracts.AbstractCardModifier;
import champ.ChampChar;
import champ.ChampMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import downfall.downfallMod;

public class GladiatorTechniqueMod extends AbstractCardModifier {

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return ChampChar.characterStrings.TEXT[11] + " NL " + rawDescription;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {

        card.tags.add(ChampMod.GLADIATOR_TECH);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GladiatorTechniqueMod();
    }
}
