package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.AbstractXAction;
import gremlin.actions.PseudoDamageRandomEnemyAction;
import theHexaghost.powers.BurnPower;

public class EmbersAction extends AbstractXAction {

    private int bonusAmt;

    private int damage;
    private AbstractPlayer p;
    private AbstractMonster m;
    private DamageInfo.DamageType damageTypeForTurn;
    private int burn;

    public EmbersAction(int bonusAmt, AbstractPlayer p, AbstractMonster m, int damage, DamageInfo.DamageType damageTypeForTurn, int burn) {
        this.bonusAmt = bonusAmt;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.damage = damage;
        this.p = p;
        this.m = m;
        this.damageTypeForTurn = damageTypeForTurn;
        this.burn = burn;
    }

    @Override
    public void initialize(int totalAmount) {
        super.initialize(totalAmount);
        this.amount += bonusAmt;
    }

    public void update() {
        for (int i = 0; i < amount; i++) {
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                    addToTop(new ApplyPowerAction(m, p, new BurnPower(m, burn), burn));
                    addToTop(new PseudoDamageRandomEnemyAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.FIRE));
                }
            });
        }
        this.isDone = true;
    }
}