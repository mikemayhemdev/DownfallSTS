package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.powers.cardpowers.EnemyMantraPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Damaru;
import com.megacrit.cardcrawl.relics.VelvetChoker;

public class CBR_Damaru extends AbstractCharbossRelic {
    public static final String ID = "Damaru";
    private int numCards;

    public CBR_Damaru() {
        super(new Damaru());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    @Override
    public void atTurnStart() {

        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
        this.addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new EnemyMantraPower(AbstractCharBoss.boss, 1), 1));

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Damaru();
    }
}
