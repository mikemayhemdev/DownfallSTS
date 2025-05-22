package awakenedOne.actions;

import awakenedOne.powers.NihilPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.WaterDropEffect;

public class NihilAction extends AbstractGameAction {
    private int damage;

    public NihilAction(AbstractCreature target, AbstractCreature source, int amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect) {
        setValues(target, source, amount);
        this.damage = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
    }

    public void update() {
        if (this.duration == 0.5F)
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        tickDuration();
        if (this.isDone) {
            vigorattack();
            this.target.damage(new DamageInfo(this.source, this.damage, this.damageType));
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
            AbstractDungeon.actionManager.addToTop((AbstractGameAction) new WaitAction(0.1F));
        }
    }

    private void vigorattack() {
        int tmp = this.damage;
        tmp -= this.target.currentBlock;
        if (tmp > this.target.currentHealth)
            tmp = this.target.currentHealth;
        if (tmp > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, new NihilPower(this.target, tmp), tmp, true, AbstractGameAction.AttackEffect.NONE));
            if (Settings.FAST_MODE) {
                this.addToBot(new VFXAction(this.source, new OfferingEnemyEffect(target), 0.2F));
            } else {
                this.addToBot(new VFXAction(this.source, new OfferingEnemyEffect(target), 0.5F));
            }
        }
    }


    private static class OfferingEnemyEffect extends AbstractGameEffect {
        private int count = 0;
        private float timer = 0.0F;
        private AbstractCreature target;

        public OfferingEnemyEffect(AbstractCreature target) {
            this.target = target;
        }

        public void update() {
            this.timer -= Gdx.graphics.getDeltaTime();
            if (this.timer < 0.0F) {
                this.timer += 0.3F;
                switch (this.count) {
                    case 0:
                        CardCrawlGame.sound.playA("ATTACK_FIRE", -0.5F);
                        CardCrawlGame.sound.playA("BLOOD_SPLAT", -0.75F);
                        AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(new Color(1.0F, 0.1F, 0.1F, 1.0F)));
                        AbstractDungeon.effectsQueue.add(new WaterDropEffect(target.hb.cX, target.hb.cY + 250.0F * Settings.scale));
                        break;
                    case 1:
                        AbstractDungeon.effectsQueue.add(new WaterDropEffect(target.hb.cX + 150.0F * Settings.scale, target.hb.cY - 80.0F * Settings.scale));
                        break;
                    case 2:
                        AbstractDungeon.effectsQueue.add(new WaterDropEffect(target.hb.cX - 200.0F * Settings.scale, target.hb.cY + 50.0F * Settings.scale));
                        break;
                    case 3:
                        AbstractDungeon.effectsQueue.add(new WaterDropEffect(target.hb.cX + 200.0F * Settings.scale, target.hb.cY + 50.0F * Settings.scale));
                        break;
                    case 4:
                        AbstractDungeon.effectsQueue.add(new WaterDropEffect(target.hb.cX - 150.0F * Settings.scale, target.hb.cY - 80.0F * Settings.scale));
                }

                ++this.count;
                if (this.count == 6) {
                    this.isDone = true;
                }
            }

        }

        public void render(SpriteBatch sb) {
        }

        public void dispose() {
        }
    }


}