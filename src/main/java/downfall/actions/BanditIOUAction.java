package downfall.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import downfall.vfx.BanditIOUEffect;

import java.util.Collections;

public class BanditIOUAction extends AbstractGameAction {

    public boolean shouldDamage = false;
    public boolean shouldPlayEffect = false;

    public boolean shouldPlayTextEffect = false;
    public boolean shouldFinish = false;

    public boolean screenshake = false;

    private BanditIOUEffect effect;
    private boolean initialized = false;

    public AbstractPlayer p;
    public AbstractMonster m;

    public int currentDamage;
    public AbstractGameEffect currentEffect;
    public AbstractGameEffect textEffect;

    public AbstractPower currentPower;

    public BanditIOUAction(AbstractPlayer p, AbstractMonster m) {
        this.p = p;
        this.m = m;

        //if (m == null) //SlimeboundMod.logger.info("Bandit Cutscene has a null monster!!");
        this.duration = 13F;
    }

    public void dealDamage(int damage) {
        if (m.hasPower(BufferPower.POWER_ID)) {
            m.getPower(BufferPower.POWER_ID).flash();
            m.getPower(BufferPower.POWER_ID).amount--;
            if (m.getPower(BufferPower.POWER_ID).amount <= 0) {
                m.powers.remove(m.getPower(BufferPower.POWER_ID));
            }
            return;
        }
        //SlimeboundMod.logger.info("Deal damage hit");
        if (!m.isDeadOrEscaped()) {
            ////SlimeboundMod.logger.info("Deal damage hitting:" + m.name);
            DamageInfo info = new DamageInfo(p, damage, DamageInfo.DamageType.THORNS);

            m.tint.color.set(Color.GRAY.cpy());
            m.tint.changeColor(Color.WHITE.cpy());
            m.damage(info);
        }

        if (currentPower != null) {
            applyPower();
            currentPower = null;
        }

        if (screenshake = true) {
            screenshake = false;
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
        }
    }

    public void playEffect(AbstractGameEffect effect) {
        //SlimeboundMod.logger.info("effect:" + effect);
        AbstractDungeon.effectsQueue.add(effect);
        if (shouldPlayTextEffect && textEffect != null) {
            AbstractDungeon.effectsQueue.add(textEffect);
        }

    }

    public void applyPower() {
        if (!m.isDeadOrEscaped()) {
            if (m.hasPower("Artifact")) {
                CardCrawlGame.sound.play("NULLIFY_SFX");
                m.getPower("Artifact").flashWithoutSound();
                m.getPower("Artifact").onSpecificTrigger();
                return;
            }
            if (AbstractDungeon.player.hasRelic("Ginger") && m.isPlayer && this.currentPower.ID.equals("Weakened")) {
                AbstractDungeon.player.getRelic("Ginger").flash();
                return;
            }
            m.useFastShakeAnimation(0.5F);


            AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect));

            if (m.hasPower(this.currentPower.ID)) {
                m.getPower(this.currentPower.ID).stackPower(this.currentPower.amount);

            } else {
                m.powers.add(this.currentPower);
                this.currentPower.onInitialApplication();
            }
            Collections.sort(m.powers);
            this.currentPower.flash();
            AbstractDungeon.onModifyPower();
            this.currentPower.updateDescription();
        }
    }

    @Override
    public void update() {
        if (!initialized) {
            initialized = true;

            effect = new BanditIOUEffect();
            effect.action = this;
            effect.pointyStartX = this.m.drawX - this.m.hb.width / 2 - (150F * Settings.scale);
            effect.pointyStartY = this.m.drawY;

            effect.bearStartX = this.m.drawX + (250F * Settings.scale);
            effect.bearLandX = this.m.drawX + (50F * Settings.scale);
            effect.bearLandY = this.m.drawY;

            AbstractDungeon.effectsQueue.add(effect);

        } else {
            if (shouldDamage) {
                dealDamage(currentDamage);
                shouldDamage = false;
                //SlimeboundMod.logger.info("damage action received");
            }
            if (shouldPlayEffect) {
                if (currentEffect != null) playEffect(currentEffect);
                shouldPlayEffect = false;
                //SlimeboundMod.logger.info("effect action received");
            }
            if (shouldFinish) {
                effect.doneBlasting = true;
                isDone = true;
            }
        }

        this.tickDuration();
    }
}