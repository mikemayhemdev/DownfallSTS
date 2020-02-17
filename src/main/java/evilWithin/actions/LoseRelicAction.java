package evilWithin.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import slimebound.cards.Prepare;


public class LoseRelicAction extends AbstractGameAction {
    public String relicID;

    public LoseRelicAction(String relicID) {
        this.relicID = relicID;


    }


    public void update() {

        AbstractDungeon.player.loseRelic(this.relicID);
        this.isDone = true;
    }

}



