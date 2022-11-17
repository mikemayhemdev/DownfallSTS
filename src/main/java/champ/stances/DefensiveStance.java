package champ.stances;

import champ.ChampChar;
import champ.ChampMod;
import champ.cards.stancecards.BringItOn;
import champ.cards.stancecards.Posture;
import champ.cards.stancecards.RopeADope;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Footwork;
import com.megacrit.cardcrawl.cards.purple.Protect;
import com.megacrit.cardcrawl.cards.red.Impervious;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import downfall.downfallMod;
import downfall.vfx.DefensiveModeStanceParticleEffect;

import java.util.ArrayList;

public class DefensiveStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:DefensiveStance";

    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(STANCE_ID);

    public DefensiveStance() {
        super(STANCE_ID);
        this.name = uiStrings.TEXT[0];
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = uiStrings.TEXT[1];
    }

    @Override
    public ArrayList<AbstractCard> getCards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        retVal.add(CardLibrary.getCard(Posture.ID).makeCopy());
        retVal.add(CardLibrary.getCard(BringItOn.ID).makeCopy());
        retVal.add(CardLibrary.getCard(RopeADope.ID).makeCopy());
        return retVal;
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
