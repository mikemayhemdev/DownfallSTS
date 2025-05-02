package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.actions.TackleSelfDamageAction;

public class BleedDebuff extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = BleedDebuff.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public BleedDebuff(int amount) {
        super(NAME, PowerType.DEBUFF, false, AbstractDungeon.player, null, amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new TackleSelfDamageAction(new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            this.addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
