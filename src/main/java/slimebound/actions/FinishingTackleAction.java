package slimebound.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class FinishingTackleAction extends AbstractGameAction {
    private static final float DURATION = 0.1F;
    private int block;
    private DamageInfo info;
    private AbstractPlayer p;

    public FinishingTackleAction(AbstractPlayer p, AbstractCreature target, DamageInfo info, int block) {
        this.info = info;
        setValues(target, info);
        this.block = block;
        this.actionType = ActionType.DAMAGE;
        this.p = p;
        this.duration = 0.1F;
    }

    public void update() {
        if ((this.duration == 0.1F) &&
                (this.target != null)) {
            AbstractDungeon.effectList.add(new WeightyImpactEffect(this.target.hb.cX, this.target.hb.cY, Color.GREEN));

            this.target.damage(this.info);

            if (((((AbstractMonster) this.target).isDying) || (this.target.currentHealth <= 0)) && (!this.target.halfDead))
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));


            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }


        tickDuration();
    }
}

