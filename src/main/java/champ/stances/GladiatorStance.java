package champ.stances;

import champ.ChampChar;
import champ.ChampMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.cards.colorless.Panacea;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

public class GladiatorStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:GladiatorStance";

    public GladiatorStance() {
        this.ID = STANCE_ID;
        this.name = ChampChar.characterStrings.TEXT[3];
        this.updateDescription();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<AbstractCard> getCards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        retVal.add(CardLibrary.getCard(Madness.ID).makeCopy());
        retVal.add(CardLibrary.getCard(Panacea.ID).makeCopy());
        retVal.add(CardLibrary.getCard(Wound.ID).makeCopy());
        retVal.add(CardLibrary.getCard(Apotheosis.ID).makeCopy());
        retVal.add(CardLibrary.getCard(CurseOfTheBell.ID).makeCopy());
        return retVal;
    }

    @Override
    public void onEnterStance() {
        super.onEnterStance();
        ChampMod.enteredGladiatorThisTurn = true;
    }
}
