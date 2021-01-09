package guardian.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;
import guardian.powers.DefensiveModeBooster;
import guardian.powers.DontLeaveDefensiveModePower;
import guardian.relics.DefensiveModeMoreBlock;
import guardian.vfx.DefensiveModeStanceParticleEffect;

public class DefensiveMode extends AbstractStance {

    public static final String STANCE_ID = "guardianmod:DefensiveMode";
    private static long sfxId = -1L;

    public DefensiveMode() {
        this.ID = STANCE_ID;// 21
        this.name = GuardianCharacter.charStrings.TEXT[4];
        this.updateDescription();// 23
    }// 24

    @Override
    public void updateAnimation() {
        if (!(AbstractDungeon.player instanceof GuardianCharacter)) {
            if (!Settings.DISABLE_EFFECTS) {

                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                    this.particleTimer = 0.04F;
                    AbstractDungeon.effectsQueue.add(new DefensiveModeStanceParticleEffect(new Color(1.0F, 0.9F, 0.7F, 0.0F)));
                }
            }


            this.particleTimer2 -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer2 < 0.0F) {
                this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
                AbstractDungeon.effectsQueue.add(new StanceAuraEffect("DefensiveMode"));
            }
        }
    }


    @Override
    public void onEnterStance() {
        if (sfxId != -1L) {
            stopIdleSfx();
        }

        CardCrawlGame.sound.play("GUARDIAN_ROLL_UP");

        if (!(AbstractDungeon.player instanceof GuardianCharacter)) {
//             CardCrawlGame.sound.play("STANCE_ENTER_CALM");
            sfxId = CardCrawlGame.sound.playAndLoop(GuardianMod.makeID("STANCE_LOOP_Defensive_Mode"));
        }

        AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, new IntenseZoomEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, false), 0.05F, true));
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLDENROD, true));

        if (AbstractDungeon.player instanceof GuardianCharacter) {
            ((GuardianCharacter) AbstractDungeon.player).switchToDefensiveMode();
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, 3), 3));
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof DefensiveModeBooster) {
                ((DefensiveModeBooster) p).onEnter();
            }
        }
    }


    @Override
    public void onExitStance() {
        stopIdleSfx();

        if (AbstractDungeon.player instanceof GuardianCharacter) {
            ((GuardianCharacter) AbstractDungeon.player).switchToOffensiveMode();
        }
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, ThornsPower.POWER_ID, 3));
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof DefensiveModeBooster) {
                ((DefensiveModeBooster) p).onLeave();
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, DontLeaveDefensiveModePower.POWER_ID));

    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop(GuardianMod.makeID("STANCE_LOOP_Defensive_Mode"), sfxId);
            sfxId = -1L;
        }
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 10));


    }

    public void onEndOfRound() {
        if (AbstractDungeon.player.hasPower(DontLeaveDefensiveModePower.POWER_ID)) {
            if (AbstractDungeon.player.getPower(DontLeaveDefensiveModePower.POWER_ID).amount > 1) {
                AbstractDungeon.player.getPower(DontLeaveDefensiveModePower.POWER_ID).flash();
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, DontLeaveDefensiveModePower.POWER_ID, 1));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(NeutralStance.STANCE_ID));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, DontLeaveDefensiveModePower.POWER_ID));
            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = GuardianCharacter.charStrings.TEXT[5];
    }
}
