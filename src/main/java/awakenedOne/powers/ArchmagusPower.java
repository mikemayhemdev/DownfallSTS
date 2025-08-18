package awakenedOne.powers;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;

import static awakenedOne.util.Wiz.atb;

public class ArchmagusPower extends AbstractTwoAmountAwakenedPower {
    // intellij stuff buff
    public static final String NAME = ArchmagusPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);


    private int cardsDoubledThisTurn = 0;
    public ArchmagusPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
        amount2 = 1 - AwakenedOneMod.spellsThisTurn;
        if (amount2 < 0) amount2 = 0;
        updateDescription();

    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        amount2 += stackAmount;
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        amount2 = amount;
        cardsDoubledThisTurn = 0;
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        SlimeboundMod.logger.info(AwakenedOneMod.spellsThisTurn );
        SlimeboundMod.logger.info(this.cardsDoubledThisTurn  );
        SlimeboundMod.logger.info( this.amount2);
        if (!card.purgeOnUse && this.amount2 > 0 && (card instanceof AbstractSpellCard) && AwakenedOneMod.spellsThisTurn - this.cardsDoubledThisTurn <= this.amount2) {
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster) action.target;
            }

            if (m == null) {
                m = AbstractDungeon.getMonsters().getRandomMonster(true);
            }

            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            --this.amount2;
            updateDescription();
        }

    }


    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[3] + Math.min(amount2, AwakenedOneMod.spellsThisTurn - this.cardsDoubledThisTurn) + DESCRIPTIONS[4];
        } else {
            description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + Math.min(amount2, AwakenedOneMod.spellsThisTurn - this.cardsDoubledThisTurn) + DESCRIPTIONS[4];
        }
    }
}