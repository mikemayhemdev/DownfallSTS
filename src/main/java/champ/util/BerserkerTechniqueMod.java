package champ.util;

import basemod.abstracts.AbstractCardModifier;
import champ.ChampChar;
import champ.ChampMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import downfall.downfallMod;

public class BerserkerTechniqueMod extends AbstractCardModifier {

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return ChampChar.characterStrings.TEXT[9] + " NL " + rawDescription;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {

        card.tags.add(ChampMod.BERSERKER_TECH);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BerserkerTechniqueMod();
    }
}
