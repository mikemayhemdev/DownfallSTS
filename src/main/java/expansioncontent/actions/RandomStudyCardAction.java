package expansioncontent.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import expansioncontent.cards.StudyTheSpire;


public class RandomStudyCardAction extends AbstractGameAction {
    public boolean upgradeCard;

    public RandomStudyCardAction(boolean upgraded) {
        this.upgradeCard = upgraded;


    }


    public void update() {

        AbstractCard c;
        //Random random = new Random();
        //Integer chosenRand = random.nextInt(8);
        c = CardLibrary.getCard(StudyTheSpire.ID).makeCopy();



        if (upgradeCard) {
            c.upgrade();
        }
        c.modifyCostForCombat(-9);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c));

        this.isDone = true;
    }

}



