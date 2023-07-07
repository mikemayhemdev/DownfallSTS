package collector.actions;

import collector.effects.ShowCardAndAddToCollectedPileEffect;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class MakeTempCardInCollectionAction extends AbstractGameAction {
    private AbstractCard cardToMake;

    public MakeTempCardInCollectionAction(AbstractCard card, int amount) {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        this.duration = this.startDuration;
        this.cardToMake = card;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            for (int i = 0; i < this.amount; ++i) {
                AbstractCard c = this.cardToMake.makeStatEquivalentCopy();
                if (c.type != CardType.CURSE && c.type != CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
                    c.upgrade();
                }

                AbstractDungeon.effectList.add(new ShowCardAndAddToCollectedPileEffect(cardToMake));
            }

            this.duration -= Gdx.graphics.getDeltaTime();
        }

        this.tickDuration();
    }
}
