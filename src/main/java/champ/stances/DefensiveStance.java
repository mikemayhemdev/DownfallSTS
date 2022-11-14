package champ.stances;

import champ.ChampChar;
import champ.ChampMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Footwork;
import com.megacrit.cardcrawl.cards.purple.Protect;
import com.megacrit.cardcrawl.cards.red.Impervious;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import downfall.downfallMod;
import downfall.vfx.DefensiveModeStanceParticleEffect;

import java.util.ArrayList;

public class DefensiveStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:DefensiveStance";

    public DefensiveStance() {
        this.ID = STANCE_ID;
        this.name = ChampChar.characterStrings.TEXT[4];
        this.updateDescription();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<AbstractCard> getCards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        retVal.add(CardLibrary.getCard(Footwork.ID).makeCopy());
        retVal.add(CardLibrary.getCard(Protect.ID).makeCopy());
        retVal.add(CardLibrary.getCard(Impervious.ID).makeCopy());
        return retVal;
    }

    @Override
    public void onEnterStance() {
        super.onEnterStance();
        ChampMod.enteredDefensiveThisTurn = true;
    }

    @Override
    public void updateAnimation() {
        if (!(AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.THE_CHAMP))) {
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
                AbstractDungeon.effectsQueue.add(new StanceAuraEffect(STANCE_ID));
            }
        }
    }
}
