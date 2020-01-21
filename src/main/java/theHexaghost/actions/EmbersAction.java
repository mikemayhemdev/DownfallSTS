package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EmbersAction extends AbstractXAction {

    private int bonusAmt;

    private int damage;
    private AbstractPlayer p;
    private AbstractMonster m;
    private DamageInfo.DamageType damageTypeForTurn;

    public EmbersAction(int bonusAmt, AbstractPlayer p, AbstractMonster m, int damage, DamageInfo.DamageType damageTypeForTurn) {
        this.bonusAmt = bonusAmt;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.damage = damage;
        this.p = p;
        this.m = m;
        this.damageTypeForTurn = damageTypeForTurn;
    }

    @Override
    public void initialize(int totalAmount) {
        super.initialize(totalAmount);
        this.amount += bonusAmt;
    }

    public void update() {
        for (int i = 0; i < amount; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.FIRE));
            addToBot(new BurnAction(m, 4));
        }

        this.isDone = true;
    }
}