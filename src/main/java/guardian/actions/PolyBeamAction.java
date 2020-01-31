//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class PolyBeamAction extends AbstractGameAction {
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private DamageInfo info;
    private int numTimes;

    public PolyBeamAction(AbstractCreature target, DamageInfo info, int numTimes) {
        this.info = info;
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.SLASH_HORIZONTAL;
        this.duration = 0.01F;
        this.numTimes = numTimes;
    }

    public void update() {
        if (this.target == null) {
            this.isDone = true;
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if (this.target.currentHealth > 0) {
                //this.target.damageFlash = true;
                //this.target.damageFlashFrames = 4;
                CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT", 0.5F);

                float randoX = MathUtils.random(-80, 80);
                float randoY = MathUtils.random(-80, 80);
                AbstractDungeon.topLevelEffects.add(new SmallLaserEffect(this.target.hb.cX + (randoX * Settings.scale), this.target.hb.cY + (randoY * Settings.scale), info.owner.hb.cX, info.owner.hb.cY));
                AbstractDungeon.topLevelEffects.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));

                this.info.applyPowers(this.info.owner, this.target);
                this.target.damage(this.info);
                if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    --this.numTimes;
                    AbstractDungeon.actionManager.addToTop(new PolyBeamAction(AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng), this.info, this.numTimes));
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
                }

            }

            this.isDone = true;
        }
    }
}
