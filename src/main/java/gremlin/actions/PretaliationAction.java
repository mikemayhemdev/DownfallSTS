package gremlin.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;

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
        if (this.m != null && (this.m.intent == AbstractMonster.Intent.ATTACK || this.m.intent == AbstractMonster.Intent.ATTACK_BUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
            AbstractDungeon.actionManager.addToTop(
                    new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.amount,
                            DamageInfo.DamageType.THORNS), AttackEffect.SLASH_HEAVY)
            );
        }
        this.isDone = true;
    }
}

