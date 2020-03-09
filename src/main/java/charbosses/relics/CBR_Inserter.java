package charbosses.relics;

import charbosses.actions.orb.EnemyIncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.HandDrill;
import com.megacrit.cardcrawl.relics.Inserter;

public class CBR_Inserter extends AbstractCharbossRelic {
    public static final String ID = "Inserter";

    public CBR_Inserter() {
        super(new Inserter());
        this.tier = RelicTier.RARE;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        this.counter = 0;
    }

    public void atTurnStart() {
        if (this.counter == -1) {
            this.counter += 2;
        } else {
            ++this.counter;
        }

        if (this.counter == 2) {
            this.counter = 0;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(this.owner, this));
            this.addToBot(new EnemyIncreaseMaxOrbAction(1));
        }

    }

    public AbstractRelic makeCopy() {
        return new Inserter();
    }
}
