package champ.stances;

import champ.ChampChar;
import champ.powers.FocusedGladPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import guardian.vfx.DefensiveModeStanceParticleEffect;

public class GladiatorStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:GladiatorStance";
    private static long sfxId = -1L;

    public GladiatorStance() {
        this.ID = STANCE_ID;// 21
        this.name = ChampChar.characterStrings.TEXT[5];
        this.updateDescription();// 23
    }// 24

    @Override
    public String getKeywordString() {
        return "champ:gladiator";
    }

    @Override
    public void updateDescription() {
        this.description = ChampChar.characterStrings.TEXT[8] + ": " + ChampChar.characterStrings.TEXT[14] + GladiatorStance.amount() + ChampChar.characterStrings.TEXT[48] + " NL " + ChampChar.characterStrings.TEXT[9] + ": " + ChampChar.characterStrings.TEXT[15];
    }

    public static int amount() {
        int x = 1;
        if (AbstractDungeon.player.hasPower(FocusedGladPower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(FocusedGladPower.POWER_ID).amount;
        }
        return x;
    }

    @Override
    public void technique() {
        int x = 1;
        if (AbstractDungeon.player.hasPower(FocusedGladPower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(FocusedGladPower.POWER_ID).amount;
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(x));
    }

    @Override
    public void finisher() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(AbstractDungeon.player, 1), 1));
    }


    @Override
    public void updateAnimation() {
        if (!(AbstractDungeon.player instanceof ChampChar)) {
            if (!Settings.DISABLE_EFFECTS) {

                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                    this.particleTimer = 0.04F;
                    AbstractDungeon.effectsQueue.add(new DefensiveModeStanceParticleEffect(new Color(.7F, 0.7F, 0.2F, 0.0F)));
                }
            }


            this.particleTimer2 -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer2 < 0.0F) {
                this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
                AbstractDungeon.effectsQueue.add(new StanceAuraEffect(this.STANCE_ID));
            }
        }
    }

}
