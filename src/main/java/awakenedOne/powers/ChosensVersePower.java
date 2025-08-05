package awakenedOne.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChosensVersePower extends AbstractTwoAmountAwakenedPower implements NonStackablePower {
    // intellij stuff buff
    public static final String NAME = ChosensVersePower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public ChosensVersePower(int amount, int block) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
        amount2 = block;
        updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.type != AbstractCard.CardType.ATTACK) {
            flash();
            addToBot(new DrawCardAction(1));
            addToBot(new GainBlockAction(owner, amount2));
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void updateDescription() {
        description = amount == 1 ? DESCRIPTIONS[0] + amount2 + DESCRIPTIONS[1] : DESCRIPTIONS[2] + amount + DESCRIPTIONS[3] + amount2 + DESCRIPTIONS[4];
    }

}