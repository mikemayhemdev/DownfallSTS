package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.powers.general.EnemyPoisonPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PaperCrane;
import com.megacrit.cardcrawl.relics.TwistedFunnel;

import java.util.Iterator;

public class CBR_TwistedFunnel extends AbstractCharbossRelic {
    public static final String ID = "TwistedFunnel";

    public CBR_TwistedFunnel() {
        super(new TwistedFunnel());
    }

    @Override
    public void atBattleStart() {
            this.flash();
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractCharBoss.boss, new EnemyPoisonPower(AbstractDungeon.player, AbstractCharBoss.boss, 4), 4));

    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_TwistedFunnel();
    }
}
