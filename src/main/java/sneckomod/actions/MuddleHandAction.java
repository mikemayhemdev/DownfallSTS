package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MuddleHandAction extends AbstractGameAction {
    private AbstractPlayer p;

    private boolean maxRangeModifier;

    public MuddleHandAction() {
        this(false);
    }

    public MuddleHandAction(boolean maxRangeMod) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.maxRangeModifier = maxRangeMod;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            for (AbstractCard card : this.p.hand.group) {
                addToTop(new MuddleAction(card, maxRangeModifier));
            }

            this.isDone = true;
        } else {
            this.tickDuration();
        }
    }
}