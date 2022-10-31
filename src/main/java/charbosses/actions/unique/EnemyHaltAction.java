package charbosses.actions.unique;

import charbosses.bosses.AbstractCharBoss;
import charbosses.stances.EnWrathStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnemyHaltAction extends AbstractGameAction {
    private AbstractMonster m;
    private int additionalAmt;

    public EnemyHaltAction(AbstractMonster monster, int block, int magicNumber) {
        this.m = monster;
        this.amount = block;
        this.additionalAmt = magicNumber;
    }

    public void update() {
        if (AbstractCharBoss.boss.stance instanceof EnWrathStance){
            this.addToTop(new GainBlockAction(m, m, this.amount + this.additionalAmt));
        } else {
            this.addToTop(new GainBlockAction(m, m, this.amount));
        }
        this.isDone = true;
    }
}
