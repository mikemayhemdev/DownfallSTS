package charbosses.powers.cardpowers;


import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.cards.colorless.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.Iterator;

public class EnemyMagnetismPower extends AbstractPower {
    public static final String POWER_ID = "Magnetism";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String SINGULAR_DESCRIPTION;
    public static final String PLURAL_DESCRIPTION;
    public static CardGroup srcColorlessCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);

    public EnemyMagnetismPower(AbstractCreature owner, int cardAmount) {
        this.name = NAME;
        this.ID = "Magnetism";
        this.owner = owner;
        this.amount = cardAmount;
        this.updateDescription();
        this.loadRegion("magnet");
        if (srcColorlessCardPool.isEmpty()){
            srcColorlessCardPool.addToTop(new EnBlind());
            srcColorlessCardPool.addToTop(new EnDramaticEntrance());
            srcColorlessCardPool.addToTop(new EnGoodInstincts());
            srcColorlessCardPool.addToTop(new EnPanacea());
            srcColorlessCardPool.addToTop(new EnSwiftStrike());
            srcColorlessCardPool.addToTop(new EnTrip());
            srcColorlessCardPool.addToTop(new EnSadisticNature());
            srcColorlessCardPool.addToTop(new EnHandOfGreed());
            srcColorlessCardPool.addToTop(new EnTheBomb());
            srcColorlessCardPool.addToTop(new EnBandage());
            srcColorlessCardPool.addToTop(new EnPanicButton());
        }
    }

    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();

            for(int i = 0; i < this.amount; ++i) {
                this.addToBot(new EnemyMakeTempCardInHandAction(returnBossColorlessCard().makeCopy(), 1, false));
            }
        }

    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void updateDescription() {
        if (this.amount > 1) {
            this.description = String.format(PLURAL_DESCRIPTION, this.amount);
        } else {
            this.description = String.format(SINGULAR_DESCRIPTION, this.amount);
        }

    }

    public static AbstractCard returnBossColorlessCard() {
        return returnBossColorlessCard(AbstractDungeon.cardRandomRng);
    }

    public static AbstractCard returnBossColorlessCard(Random rng) {
        ArrayList<AbstractCard> list = new ArrayList();
        Iterator var2 = srcColorlessCardPool.group.iterator();

        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            list.add(c);
        }

        return (AbstractCard)list.get(rng.random(list.size() - 1));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Magnetism");
        NAME = powerStrings.NAME;
        SINGULAR_DESCRIPTION = powerStrings.DESCRIPTIONS[0];
        PLURAL_DESCRIPTION = powerStrings.DESCRIPTIONS[1];
    }
}
