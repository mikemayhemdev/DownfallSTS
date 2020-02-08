//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import slimebound.SlimeboundMod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class DissolveAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;
    public static int numExhausted;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }

    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private boolean canPickZero;
    private int block;
    private int extraCards;

    public DissolveAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, int block, int blockUnc) {
        this(target, source, amount, isRandom, false, false, block, blockUnc);
    }

    public DissolveAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean anyNumber, boolean canPickZero, int block, int extraCards) {
        this.canPickZero = false;
        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;
        this.p = (AbstractPlayer) target;
        this.isRandom = isRandom;
        this.setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
        this.block = block;
        this.extraCards = extraCards;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }

            int i;
            if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                numExhausted = this.amount;
                int i2 = this.p.hand.size();

                for (i = 0; i < i2; ++i) {
                    AbstractCard c = this.p.hand.getTopCard();
                    this.p.hand.moveToExhaustPile(c);
                    dissolveEffect(c);

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

            for (i = 0; i < this.amount; ++i) {
                AbstractCard c = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                this.p.hand.moveToExhaustPile(c);
                dissolveEffect(c);

            }

            CardCrawlGame.dungeon.checkForPactAchievement();
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            Iterator var4 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while (var4.hasNext()) {
                AbstractCard c = (AbstractCard) var4.next();
                this.p.hand.moveToExhaustPile(c);
                dissolveEffect(c);
            }

            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    public void dissolveEffect(AbstractCard c2) {

        if (c2.cost < 0) c2.cost = 0;
        if (c2.cost + this.extraCards == 0) {
            return;
        }

        ArrayList<String> tmp = new ArrayList();
        Iterator var3 = CardLibrary.cards.entrySet().iterator();

        while (var3.hasNext()) {
            Map.Entry<String, AbstractCard> c = (Map.Entry) var3.next();
            if (c.getValue().hasTag(SlimeboundMod.LICK)) {
                tmp.add(c.getKey());
            }
        }


        if (c2.isCostModifiedForTurn) {
            this.extraCards += c2.costForTurn;
        } else {
            this.extraCards += c2.cost;
        }

        for (int i = 0; i < (this.extraCards); i++) {
            AbstractCard cZero;
            if (tmp.size() > 0) {
                cZero = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1)));
            } else {
                cZero = new Madness();
            }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cZero));

        }
        /*
        if (c.rarity == AbstractCard.CardRarity.UNCOMMON) {
            AbstractDungeon.actionManager.addToBottom(new HealAction(p,p,this.blockUnc));
        }
        if (c.rarity == AbstractCard.CardRarity.RARE) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower( p, 1), 1));

        }
        */
    }
}
