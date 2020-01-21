package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theHexaghost.powers.BurnPower;

public class ConsumeSoulAction extends AbstractGameAction {
    private DamageInfo info;
    private int wahhhh;

    public ConsumeSoulAction(AbstractCreature target, DamageInfo info, int glurb) {
        this.info = info;// 23
        this.setValues(target, info);// 24
        this.actionType = ActionType.DAMAGE;// 25
        this.duration = Settings.ACTION_DUR_MED;// 26
        wahhhh = glurb;
    }// 27

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED && this.target != null) {// 31 32
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.NONE));// 33
            this.target.damage(this.info);// 34
            if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion") && this.target.hasPower(BurnPower.POWER_ID)) {
                AbstractDungeon.player.heal(wahhhh, true);
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {// 52
                AbstractDungeon.actionManager.clearPostCombatActions();// 53
            }
        }

        this.tickDuration();// 57
    }// 64
}
