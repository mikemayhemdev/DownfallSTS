package timeeater.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import timeeater.suspend.SuspendHelper;

import java.util.Iterator;

public class EraseAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private DamageInfo info;
    private boolean canPickZero;
    public static int numExhausted;

    public EraseAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero, AbstractCreature target, DamageInfo info) {
        this.anyNumber = anyNumber;
        this.info = info;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
        this.target = target;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (SuspendHelper.suspendGroup.size() == 0) {
                this.isDone = true;
                return;
            }

            if (!this.anyNumber && SuspendHelper.suspendGroup.size() <= this.amount) {
                this.amount = SuspendHelper.suspendGroup.size();
                numExhausted = this.amount;

                for (int i = 0; i < SuspendHelper.suspendGroup.size(); ++i) {
                    AbstractCard c = SuspendHelper.suspendGroup.getTopCard();
                    SuspendHelper.suspendGroup.moveToExhaustPile(c);
                    addToTop(new DamageAction(target, info, AttackEffect.FIRE));
                }

                CardCrawlGame.dungeon.checkForPactAchievement();
                return;
            }

            if (!this.isRandom) {
                numExhausted = this.amount;
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero);
                this.tickDuration();
                return;
            }

            for (int i = 0; i < this.amount; ++i) {
                SuspendHelper.suspendGroup.moveToExhaustPile(SuspendHelper.suspendGroup.getRandomCard(AbstractDungeon.cardRandomRng));
                addToTop(new DamageAction(target, info, AttackEffect.FIRE));
            }

            CardCrawlGame.dungeon.checkForPactAchievement();
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            Iterator var4 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while (var4.hasNext()) {
                AbstractCard c = (AbstractCard) var4.next();
                SuspendHelper.suspendGroup.moveToExhaustPile(c);
                addToTop(new DamageAction(target, info, AttackEffect.FIRE));
            }

            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }
}
