package slimebound.actions;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.powers.SearingPower;


public class DividerAction extends AbstractGameAction {
    private DamageInfo info;
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private int numTimes;

    public DividerAction(AbstractCreature target, DamageInfo info, int numTimes) {
        this.info = info;
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.FIRE;
        this.duration = 0.01F;
        this.numTimes = numTimes;
    }

    public void update() {
        if (this.target == null) {
            this.isDone = true;
            return;
        }

        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
            return;
        }

        if (this.target.currentHealth > 0) {
            // this.target.damageFlash = true;
            // this.target.damageFlashFrames = 4;
            AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.combat.GhostIgniteEffect(this.target.hb.cX, this.target.hb.cY));
            if (MathUtils.randomBoolean()) {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("GHOST_ORB_IGNITE_1", 0.3F));
            } else {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("GHOST_ORB_IGNITE_2", 0.3F));
            }
            this.info.applyPowers(this.info.owner, this.target);
            this.target.damage(this.info);
          //  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.info.owner, new SearingPower(this.target, this.info.owner, 1), 1, true, AttackEffect.NONE));

            if ((this.numTimes > 1) && (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())) {
                this.numTimes -= 1;
                AbstractDungeon.actionManager.addToTop(new DividerAction(
                        AbstractDungeon.getMonsters().getRandomMonster(true), this.info, this.numTimes));
            }

            AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.utility.WaitAction(0.1F));
        }

        this.isDone = true;
    }
}

