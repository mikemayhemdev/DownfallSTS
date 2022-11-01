package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.GremlinMod;

public class PretaliationAction extends AbstractGameAction {
    private final AbstractMonster m;

    public PretaliationAction(final int dmgAmount, final AbstractMonster m) {
        this.actionType = ActionType.WAIT;
        this.amount = dmgAmount;
        this.m = m;
    }

    @Override
    public void update() {
        if (GremlinMod.doesEnemyIntendToAttack(this.m)) {
            AbstractDungeon.actionManager.addToTop(
                    new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.amount,
                            DamageInfo.DamageType.THORNS), AttackEffect.SLASH_HEAVY)
            );
        }
        this.isDone = true;
    }
}

