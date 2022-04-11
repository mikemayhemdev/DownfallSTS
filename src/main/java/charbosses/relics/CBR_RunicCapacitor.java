package charbosses.relics;

import charbosses.actions.orb.EnemyIncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Inserter;
import com.megacrit.cardcrawl.relics.RunicCapacitor;

public class CBR_RunicCapacitor extends AbstractCharbossRelic {
    public static final String ID = "Runic Capacitor";

    public CBR_RunicCapacitor() {
        super(new RunicCapacitor());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(this.owner, this));
        this.addToBot(new EnemyIncreaseMaxOrbAction(3));
    }

    public AbstractRelic makeCopy() {
        return new CBR_RunicCapacitor();
    }
}
