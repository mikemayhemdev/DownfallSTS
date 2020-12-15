package automaton.actions;

import automaton.AutomatonMod;
import automaton.cards.AbstractBronzeCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FireCardAction extends AbstractGameAction {
    //This is gonna be the best, greatest "play-a-card" action ever created in Slay the Spire history, watch me
    private AbstractCard myCard;
    private CardGroup container;

    public FireCardAction(AbstractCard c, CardGroup container, AbstractMonster m) {
        actionType = ActionType.WAIT;
        source = AbstractDungeon.player;
        myCard = c;
        target = m;
        this.container = container;
    }

    public FireCardAction(AbstractCard c, CardGroup container) {
        this(c, container, null);
    }

    public void update() {
        if (myCard instanceof AbstractBronzeCard) {
            ((AbstractBronzeCard) myCard).inFire = true;
        }
        if (myCard.hasTag(AutomatonMod.BURNOUT)) {
            myCard.exhaust = true;
        }

        container.removeCard(myCard);
        AbstractDungeon.player.limbo.addToBottom(myCard);

        myCard.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        myCard.target_y = (float) Settings.HEIGHT / 2.0F;

        myCard.applyPowers();

        if (target != null) {
            myCard.calculateCardDamage((AbstractMonster) target);
        }

        this.addToTop(new NewQueueCardAction(myCard, this.target, false, true));// 53
        this.addToTop(new UnlimboAction(myCard));// 54
        isDone = true;
    }
}
