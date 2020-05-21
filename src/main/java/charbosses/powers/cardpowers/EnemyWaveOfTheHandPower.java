package charbosses.powers.cardpowers;


import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import java.util.Iterator;

public class EnemyWaveOfTheHandPower extends AbstractPower {
    public static final String POWER_ID = "WaveOfTheHandPower";
    private static final PowerStrings powerStrings;

    public EnemyWaveOfTheHandPower(AbstractCreature owner, int newAmount) {
        this.name = powerStrings.NAME;
        this.ID = "WaveOfTheHandPower";
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.loadRegion("wave_of_the_hand");
    }

    public void onGainedBlock(float blockAmount) {
        if (blockAmount > 0.0F) {
            this.flash();
            AbstractCreature p = AbstractCharBoss.boss;
            AbstractCreature mo = AbstractDungeon.player;

            this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.amount, true), this.amount, true, AttackEffect.NONE));

        }

    }

    public void atEndOfRound() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "WaveOfTheHandPower"));
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("WaveOfTheHandPower");
    }
}
