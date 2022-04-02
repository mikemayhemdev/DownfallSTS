package timeeater.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static timeeater.TimeEaterMod.makeID;

public class TemporalBarrierPower extends AbstractTimeEaterPower {
    public static final String ID = makeID(TemporalBarrierPower.class.getSimpleName());
    public static final PowerStrings strs = CardCrawlGame.languagePack.getPowerStrings(ID);

    public TemporalBarrierPower(int amount) {
        super(ID, strs.NAME, PowerType.BUFF, true, AbstractDungeon.player, amount);
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner instanceof AbstractMonster && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {
            this.flash();
            addToBot(new ApplyPowerAction(info.owner, owner, new TurnBasedSlowPower(info.owner, amount)));
        }

        return damageAmount;
    }

    public void atStartOfTurn() {
        this.addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this.ID));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
