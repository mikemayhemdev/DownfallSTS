package automaton.powers;

import automaton.cards.FunctionCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HardenedFormPower extends AbstractAutomatonPower implements OnCompilePower {
    public static final String NAME = "HardenedForm";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public HardenedFormPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            onSpecificTrigger();
        }
    }

    @Override
    public void onSpecificTrigger() {
        flash();
        addToBot(new GainBlockAction(owner, amount));
        addToBot(new DamageRandomEnemyAction(new DamageInfo(owner, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING)); // TODO: real orb VFX

    }

    @Override
    public void onAfterCardPlayed(AbstractCard function) {
        if (function instanceof FunctionCard) {
            onSpecificTrigger();
        }
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
