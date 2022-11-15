package champ.stances;

import basemod.patches.whatmod.WhatMod;
import champ.ChampChar;
import collector.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

public class UltimateStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:UltimateStance";

    public UltimateStance() {
        this.ID = STANCE_ID;
        this.name = ChampChar.characterStrings.TEXT[7];
        this.updateDescription();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<AbstractCard> getCards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        Wiz.getCardsMatchingPredicate(c -> c.rarity.equals(AbstractCard.CardRarity.SPECIAL) && WhatMod.findModID(c.getClass()) == null, true).forEach(s -> retVal.add(CardLibrary.getCard(s).makeCopy()));
        return retVal;
    }

    @Override
    public void onEnterStance() {
        super.onEnterStance();
        //TODO: entered victorious this turn, if necessary
    }
}
