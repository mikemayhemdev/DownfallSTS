package charbosses.actions.unique;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import java.util.Iterator;

public class EnemyFletchettesAction extends AbstractGameAction {
    private DamageInfo info;

    public EnemyFletchettesAction(final AbstractCreature target, final DamageInfo info) {
        this.setValues(target, this.info = info);
        this.duration = Settings.ACTION_DUR_XFAST;
        this.info = info;
        this.actionType = ActionType.BLOCK;
        this.target = target;
    }

    @Override
    public void update() {
        Iterator var1 = AbstractCharBoss.boss.hand.group.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.type == AbstractCard.CardType.SKILL) {
                this.addToTop(new DamageAction(this.target, this.info, true));
                if (this.target != null && this.target.hb != null) {
                    this.addToTop(new VFXAction(new ThrowDaggerEffect(this.target.hb.cX, this.target.hb.cY)));
                }
            }
        }

        this.isDone = true;
    }
}
