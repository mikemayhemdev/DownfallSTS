package champ.stances;

import champ.cards.stancecards.AdoringFans;
import champ.cards.stancecards.TheTrophy;
import champ.cards.stancecards.VictorsPose;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class UltimateStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:UltimateStance";

    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(STANCE_ID);

    public UltimateStance() {
        this.ID = STANCE_ID;
        this.name = uiStrings.TEXT[0];
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = uiStrings.TEXT[1];
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
