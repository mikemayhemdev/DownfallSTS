package twins.actions;

import twins.DonuDecaMod;
import twins.cards.AbstractTwinsCard;
import twins.powers.OnFireSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

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
        if (target == null) {
            target = AbstractDungeon.getRandomMonster();
        }

        if (myCard instanceof AbstractTwinsCard) {
            ((AbstractTwinsCard) myCard).inFire = true;
        }
        if (myCard.hasTag(DonuDecaMod.BURNOUT)) {
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

        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnFireSubscriber) {
                ((OnFireSubscriber) p).onFire(myCard);
            }
        }

        this.addToTop(new NewQueueCardAction(myCard, this.target, false, false));// 53
        this.addToTop(new UnlimboAction(myCard));// 54
        isDone = true;
    }
}
