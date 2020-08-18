package downfall.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.cards.KnowingSkullWish;
import downfall.cards.OctoChoiceCard;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;
import expansioncontent.patches.CenterGridCardSelectScreen;
import slimebound.cards.Split;

public class OctoChoiceActionSplit extends AbstractGameAction {
    private boolean pickCard = false;
    private CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private Split funCard;
    public String[] TEXT = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("OctoChoiceAction")).TEXT;

    public OctoChoiceActionSplit(Split card) {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
        funCard = card;
    }

    @Override
    public void update() {
        for (OctoChoiceCard q : funCard.choiceList()) {
            group.addToTop(q);
        }
        if (duration == Settings.ACTION_DUR_XFAST && !group.isEmpty()) {
            pickCard = true;
            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, 1, (TEXT[2]), false);
        } else if ((pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())) {
            OctoChoiceCard cardChoice = new OctoChoiceCard("null", "null", expansionContentMod.makeCardPath("AwakenDeath.png"), TEXT[1]);
            if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                pickCard = false;
                cardChoice = (OctoChoiceCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                CenterGridCardSelectScreen.centerGridSelect = false;
            }
            funCard.doChoiceStuff(cardChoice);

            isDone = true;
        } else if (group.isEmpty()) {
            isDone = true;
        }
        tickDuration();
    }
}