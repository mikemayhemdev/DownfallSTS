package downfall.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;

public class OctoChoiceAction extends AbstractGameAction {
    private boolean pickCard = false;
    private final CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private final OctopusCard funCard;
    public String[] TEXT = CardCrawlGame.languagePack.getUIString("downfall:OctoChoiceAction").TEXT;

    public OctoChoiceAction(AbstractMonster m, OctopusCard card) {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
        funCard = card;
        target = m;
    }

    @Override
    public void update() {
        /*  TODO - This has a lot of expansioncontentmod references and is the old OctoChoice.  Probably can just be rebuilt.
        if (funCard instanceof AbstractCard) {
            ((AbstractCard) funCard).applyPowers();
            ((AbstractCard) funCard).calculateCardDamage((AbstractMonster) target);
        }
        for (OctoChoiceCard q : funCard.choiceList()) {
            group.addToTop(q);
        }
        if (duration == Settings.ACTION_DUR_XFAST && !group.isEmpty()) {
            pickCard = true;
            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, 1, (TEXT[0]), false);
        } else if ((pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())) {
            OctoChoiceCard cardChoice = new OctoChoiceCard("null", "null", expansionContentMod.makeCardPath("AwakenDeath.png"), TEXT[1]);
            if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                pickCard = false;
                cardChoice = (OctoChoiceCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                CenterGridCardSelectScreen.centerGridSelect = false;
            }
            funCard.doChoiceStuff((AbstractMonster) target, cardChoice);

            isDone = true;
        } else if (group.isEmpty()) {
            isDone = true;
        }
        tickDuration();

         */
    }
}