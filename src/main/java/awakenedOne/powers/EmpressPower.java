package awakenedOne.powers;

import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;

import static awakenedOne.AwakenedOneMod.HexCurse;

public class EmpressPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = EmpressPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public EmpressPower(AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }


    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if (card instanceof AbstractSpellCard) {
            flash();
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, this.amount), this.amount));
        }
    }


    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
        else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

}