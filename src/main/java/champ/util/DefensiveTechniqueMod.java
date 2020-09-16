package champ.util;

import basemod.abstracts.AbstractCardModifier;
import champ.ChampChar;
import champ.ChampMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import downfall.downfallMod;

public class DefensiveTechniqueMod extends AbstractCardModifier {

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return ChampChar.characterStrings.TEXT[10] + " NL " + rawDescription;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(ChampMod.DEFENSIVE_TECH);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DefensiveTechniqueMod();
    }
}
