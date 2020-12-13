package champ.stances;

import champ.ChampChar;
import champ.powers.CounterPower;
import champ.relics.DefensiveTrainingManual;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import guardian.vfx.DefensiveModeStanceParticleEffect;

public class DefensiveStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:DefensiveStance";
    private static long sfxId = -1L;

    public DefensiveStance() {
        this.ID = STANCE_ID;
        this.name = ChampChar.characterStrings.TEXT[4];
        this.updateDescription();
    }

    @Override
    public String getKeywordString() {
        return "champ:defensive";
    }

    @Override
    public void updateDescription() {
        this.description = ChampChar.characterStrings.TEXT[8] + ": " + ChampChar.characterStrings.TEXT[12] + " NL " + ChampChar.characterStrings.TEXT[9] + ": " + ChampChar.characterStrings.TEXT[13];
    }

    @Override
    public void technique() {
        if (AbstractDungeon.player.hasRelic(DefensiveTrainingManual.ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CounterPower(9), 9));

        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CounterPower(6), 6));

        }
    }

    @Override
    public void finisher() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, 12));
    }


    @Override
    public void updateAnimation() {
        if (!(AbstractDungeon.player instanceof ChampChar)) {
            if (!Settings.DISABLE_EFFECTS) {

                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                    this.particleTimer = 0.04F;
                    AbstractDungeon.effectsQueue.add(new DefensiveModeStanceParticleEffect(new Color(.2F, 0.2F, 1F, 0.0F)));
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
