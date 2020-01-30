package charbosses.actions.common;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import charbosses.actions.vfx.cardmanip.EnemyShowCardAndAddToDrawPileEffect;
import charbosses.bosses.AbstractCharBoss;

import com.badlogic.gdx.*;

public class EnemyMakeTempCardInDrawPileAction extends AbstractGameAction
{
    private AbstractCard cardToMake;
    private boolean randomSpot;
    private boolean autoPosition;
    private boolean toBottom;
    private float x;
    private float y;
    
    public EnemyMakeTempCardInDrawPileAction(final AbstractCard card, final int amount, final boolean randomSpot, final boolean autoPosition, final boolean toBottom, final float cardX, final float cardY) {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = (Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5f);
        this.duration = this.startDuration;
        this.cardToMake = card;
        this.randomSpot = randomSpot;
        this.autoPosition = autoPosition;
        this.toBottom = toBottom;
        this.x = cardX;
        this.y = cardY;
    }
    
    public EnemyMakeTempCardInDrawPileAction(final AbstractCard card, final int amount, final boolean randomSpot, final boolean autoPosition, final boolean toBottom) {
        this(card, amount, randomSpot, autoPosition, toBottom, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f);
    }
    
    public EnemyMakeTempCardInDrawPileAction(final AbstractCard card, final int amount, final boolean shuffleInto, final boolean autoPosition) {
        this(card, amount, shuffleInto, autoPosition, false);
    }
    
    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (this.amount < 6) {
                for (int i = 0; i < this.amount; ++i) {
                    final AbstractCard c = this.cardToMake.makeStatEquivalentCopy();
                    if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS && AbstractCharBoss.boss.hasPower("MasterRealityPower")) {
                        c.upgrade();
                    }
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDrawPileEffect(c, this.x, this.y, this.randomSpot, this.autoPosition, this.toBottom));
                }
            }
            else {
                for (int i = 0; i < this.amount; ++i) {
                    final AbstractCard c = this.cardToMake.makeStatEquivalentCopy();
                    if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS && AbstractCharBoss.boss.hasPower("MasterRealityPower")) {
                        c.upgrade();
                    }
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDrawPileEffect(c, this.randomSpot, this.toBottom));
                }
            }
            this.duration -= Gdx.graphics.getDeltaTime();
        }
        this.tickDuration();
    }
}
