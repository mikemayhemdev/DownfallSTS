package charbosses.actions.common;

import charbosses.actions.vfx.cardmanip.EnemyShowCardAndAddToDiscardEffect;
import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class EnemyMakeTempCardInDiscardAction extends AbstractGameAction {
    private AbstractCard c;
    private int numCards;
    private boolean sameUUID;

    public EnemyMakeTempCardInDiscardAction(final AbstractCard card, final int amount) {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.numCards = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = (Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5f);
        this.duration = this.startDuration;
        this.c = card;
        this.sameUUID = false;
    }

    public EnemyMakeTempCardInDiscardAction(final AbstractCard card, final boolean sameUUID) {
        this(card, 1);
        this.sameUUID = sameUUID;
        if (!sameUUID && this.c.type != AbstractCard.CardType.CURSE && this.c.type != AbstractCard.CardType.STATUS && AbstractCharBoss.boss.hasPower("MasterRealityPower")) {
            this.c.upgrade();
        }
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (this.numCards < 6) {
                for (int i = 0; i < this.numCards; ++i) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard()));
                }
            }
            this.duration -= Gdx.graphics.getDeltaTime();
        }
        this.tickDuration();
    }

    private AbstractCard makeNewCard() {
        if (this.sameUUID) {
            return this.c.makeSameInstanceOf();
        }
        return this.c.makeStatEquivalentCopy();
    }
}
