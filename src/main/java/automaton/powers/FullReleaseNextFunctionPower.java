package automaton.powers;

import automaton.cards.Flail;
import automaton.cards.FunctionCard;
import automaton.cards.WhirlingStrike;
import automaton.vfx.PiercingShotEffect;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FullReleaseNextFunctionPower extends AbstractAutomatonPower {
    public static final String NAME = "FullReleaseNextFunctionPower";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;


    public FullReleaseNextFunctionPower() {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, -1);
        updateDescription();
    }


    @Override
    public void updateDescription() {
        if (amount > 1) {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0];
        }

    }
}
