package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gremlin.relics.FragmentationGrenade;

public class PseudoDamageRandomEnemyAction extends AbstractGameAction {
    private DamageInfo info;

    public PseudoDamageRandomEnemyAction(AbstractCreature target, DamageInfo info, AttackEffect effect) {
        this.target = target;
        this.info = info;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    public PseudoDamageRandomEnemyAction(AbstractCreature target, DamageInfo info) {
        this(target, info, AttackEffect.NONE);
    }

    public void update() {
        if (AbstractDungeon.player.hasRelic(FragmentationGrenade.ID)) {
            AbstractDungeon.player.getRelic(FragmentationGrenade.ID).flash();
            DamageInfo old = this.info;
            this.info = new DamageInfo(old.owner, old.base + FragmentationGrenade.OOMPH, old.type);
        }

        this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));

        this.isDone = true;
    }
}
