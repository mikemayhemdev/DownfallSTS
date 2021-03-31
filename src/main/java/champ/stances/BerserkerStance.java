package champ.stances;

import champ.ChampChar;
import champ.ChampMod;
import champ.powers.BerserkerStylePower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import guardian.vfx.DefensiveModeStanceParticleEffect;

import static champ.ChampMod.fatigue;
import static champ.ChampMod.vigor;

public class BerserkerStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:BerserkerStance";
    private static long sfxId = -1L;

    public BerserkerStance() {
        this.ID = STANCE_ID;// 21
        this.name = ChampChar.characterStrings.TEXT[3];
        this.updateDescription();// 23
    }// 24

    @Override
    public String getKeywordString() {
        return "champ:berserker";
    }

    @Override
    public void updateDescription() {
        this.description = ChampChar.characterStrings.TEXT[8] + ": "   //Technique Bonus:
                + ChampChar.characterStrings.TEXT[12] + //Gain #B
                BerserkerStance.amount() +
                ChampChar.characterStrings.TEXT[46] + //#y Vigor.
                " NL " + ChampChar.characterStrings.TEXT[9] + ": " + //Finisher Bonus:
                ChampChar.characterStrings.TEXT[11];   //Gain 1 Strength.
    }

    @Override
    public void onEnterStance() {
        super.onEnterStance();
        ChampMod.enteredBerserkerThisTurn = true;
    }

    public static int amount() {
        int x = 4;
        if (AbstractDungeon.player.hasPower(BerserkerStylePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(BerserkerStylePower.POWER_ID).amount;
        }
        return x;
    }

    @Override
    public void technique() {
        vigor(amount());
    }

    @Override
    public void finisher() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));

    }

    @Override
    public void updateAnimation() {
        if (!(AbstractDungeon.player instanceof ChampChar)) {
            if (!Settings.DISABLE_EFFECTS) {

                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                    this.particleTimer = 0.04F;
                    AbstractDungeon.effectsQueue.add(new DefensiveModeStanceParticleEffect(new Color(1.0F, 0.2F, 0.2F, 0.0F)));
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
