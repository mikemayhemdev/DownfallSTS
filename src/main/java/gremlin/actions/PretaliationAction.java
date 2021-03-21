package gremlin.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import gremlin.GremlinMod;

public class PretaliationAction extends AbstractGameAction
{
    private AbstractMonster m;

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

