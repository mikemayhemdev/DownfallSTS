package awakenedOne.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.powers.SnipePower;

import static awakenedOne.AwakenedOneMod.HexCurse;

public class EnemyHexedPower extends AbstractAwakenedPower implements HealthBarRenderPower {
    // intellij stuff buff
    public static final String NAME = EnemyHexedPower.class.getSimpleName();
    public static final String POWER_ID = "awakened:EnemyHexedPower";

    public EnemyHexedPower(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.DEBUFF, false, owner, null, amount);
        this.loadRegion("hex");
        if (owner.hasPower(UltimateHexDebuff.POWER_ID)) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, EnemyHexedPower.POWER_ID));
        }
        updateDescription();
    }

    @Override
    public int getHealthBarAmount() {
        return (owner.currentHealth/10)*this.amount;
    }

    @Override
    public Color getColor() {
        return Color.PURPLE.cpy();
    }


    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.type != AbstractCard.CardType.ATTACK) {
            this.flash();

            if (card.type != AbstractCard.CardType.POWER) {
                HexCurse(1, owner, owner);
            }

            if (card.type == AbstractCard.CardType.POWER) {
                HexCurse(3, this.owner, owner);
            }
        }
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.owner.hasPower(UltimateHexDebuff.POWER_ID)) {
        this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, EnemyHexedPower.POWER_ID));
        }


        if (this.amount >= 10) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, EnemyHexedPower.POWER_ID));
            this.addToTop(new ApplyPowerAction(this.owner, AbstractDungeon.player, new UltimateHexDebuff(this.owner, 1), 1));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}