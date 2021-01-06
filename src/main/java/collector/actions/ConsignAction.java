package collector.actions;

import collector.powers.SoulSnare;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class ConsignAction extends AbstractGameAction {
    private int increaseAmount;
    private DamageInfo info;;
    public int damage;
    public int healAmount = 0;
    public ConsignAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HORIZONTAL));
            target.damage(new DamageInfo(this.source, this.damage, this.damageType));
            if (target.lastDamageTaken > 0) {
                healAmount += target.lastDamageTaken;
            }
            if (healAmount > 0) {
                addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new SoulSnare(healAmount,target)));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}
