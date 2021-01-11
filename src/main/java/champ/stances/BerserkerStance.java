package champ.stances;

import champ.ChampChar;
import champ.ChampMod;
import champ.powers.BerserkerStylePower;
import champ.powers.ResolvePower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import guardian.vfx.DefensiveModeStanceParticleEffect;

import static champ.ChampMod.fatigue;

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
        this.description = ChampChar.characterStrings.TEXT[8] + ": " + ChampChar.characterStrings.TEXT[10] + amount() + ChampChar.characterStrings.TEXT[48] + " NL " + ChampChar.characterStrings.TEXT[9] + ": " + ChampChar.characterStrings.TEXT[11];
    }

    @Override
    public void onEnterStance() {
        super.onEnterStance();
        ChampMod.enteredBerserkerThisTurn = true;
    }

    public static int amount() {
        int x = 5;
        if (AbstractDungeon.player.hasPower(BerserkerStylePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(BerserkerStylePower.POWER_ID).amount;
        }
        return x;
    }

    @Override
    public void technique() {
        fatigue(amount());
    }

    @Override
    public void finisher() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (AbstractDungeon.player.hasPower(ResolvePower.POWER_ID)) {
                    AbstractPower q = AbstractDungeon.player.getPower(ResolvePower.POWER_ID);
                    if (q instanceof ResolvePower) {
                        ((ResolvePower) q).adjustStrength = false;
                        int x = AbstractDungeon.player.getPower(ResolvePower.POWER_ID).amount;
                        if (x > 0) {
                            addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, x));
                            addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.getPower(ResolvePower.POWER_ID)));
                        }
                    }
                }
            }
        });
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
