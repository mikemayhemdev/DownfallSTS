package champ.stances;

import basemod.patches.whatmod.WhatMod;
import champ.ChampChar;
import champ.cards.stancecards.AdoringFans;
import champ.cards.stancecards.TheTrophy;
import champ.cards.stancecards.VictorsPose;
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
        retVal.add(CardLibrary.getCard(VictorsPose.ID).makeCopy());
        retVal.add(CardLibrary.getCard(AdoringFans.ID).makeCopy());
        retVal.add(CardLibrary.getCard(TheTrophy.ID).makeCopy());
        return retVal;
    }

}
