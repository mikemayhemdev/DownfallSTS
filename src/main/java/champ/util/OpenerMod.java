package champ.util;

import basemod.abstracts.AbstractCardModifier;
import champ.ChampChar;
import champ.ChampMod;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class OpenerMod extends AbstractCardModifier {

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return ChampChar.characterStrings.TEXT[12] + " NL " + rawDescription;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {

        card.tags.add(ChampMod.OPENER);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new OpenerMod();
    }
}
