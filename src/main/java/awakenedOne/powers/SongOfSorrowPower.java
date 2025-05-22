package awakenedOne.powers;

import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.atb;

public class SongOfSorrowPower extends AbstractAwakenedPower implements OnLoseEnergyPower {
    // intellij stuff buff
    public static final String NAME = SongOfSorrowPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public SongOfSorrowPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void LoseEnergyAction(int gained) {
        this.flash();
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new ManaburnPower(m, amount), amount));
    }

    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
        else {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3];
        }
    }

}