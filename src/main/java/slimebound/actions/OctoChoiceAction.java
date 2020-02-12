package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import expansioncontent.cards.OctoChoiceCard;
import expansioncontent.expansionContentMod;
import expansioncontent.patches.CenterGridCardSelectScreen;
import slimebound.cards.SpecialForces;
import slimebound.cards.SplitSpecialist;

public class OctoChoiceAction extends AbstractGameAction {
    private boolean pickCard = false;
    private CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private SplitSpecialist funCard;
    private SpecialForces funCard2;

    public OctoChoiceAction(SplitSpecialist card) {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
        funCard = card;
    }


    public OctoChoiceAction(SpecialForces card) {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
        funCard2 = card;
    }

    @Override
    public void update() {
        if (funCard != null){
            for (OctoChoiceCard q : funCard.choiceList()) {
                group.addToTop(q);
            }
        } else {
            for (OctoChoiceCard q : funCard2.choiceList()) {
                group.addToTop(q);
            }
        }


        if (duration == Settings.ACTION_DUR_XFAST && !group.isEmpty()) {
            pickCard = true;
            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, 1, ("Choose a Boss to Study."), false);
        } else if ((pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())) {
            OctoChoiceCard cardChoice = new OctoChoiceCard("null", "null", expansionContentMod.makeCardPath("AwakenDeath.png"), "You should never see this.");
            if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                pickCard = false;
                cardChoice = (OctoChoiceCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                CenterGridCardSelectScreen.centerGridSelect = false;
            }
            if (funCard != null){
                funCard.doChoiceStuff(cardChoice);
            } else {
                funCard2.doChoiceStuff(cardChoice);
            }

            isDone = true;
        } else if (group.isEmpty()) {
            isDone = true;
        }
        tickDuration();
    }
}