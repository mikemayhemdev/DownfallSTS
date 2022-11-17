package champ.stances;

import champ.ChampChar;
import champ.ChampMod;
import champ.cards.stancecards.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.cards.colorless.Panacea;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import downfall.downfallMod;
import downfall.vfx.DefensiveModeStanceParticleEffect;

import java.util.ArrayList;

public class GladiatorStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:GladiatorStance";

    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(STANCE_ID);

    public GladiatorStance() {
        super(STANCE_ID);
        this.name = uiStrings.TEXT[0];
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = uiStrings.TEXT[1];
    }

    @Override
    public ArrayList<AbstractCard> getCards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        retVal.add(CardLibrary.getCard(Invigorate.ID).makeCopy());
        retVal.add(CardLibrary.getCard(QuickStep.ID).makeCopy());
        retVal.add(CardLibrary.getCard(Piledriver.ID).makeCopy());
        return retVal;
    }

    @Override
    public void updateAnimation() {
        if (!(AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.THE_CHAMP))) {
            if (!Settings.DISABLE_EFFECTS) {

                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                    this.particleTimer = 0.04F;
                    AbstractDungeon.effectsQueue.add(new DefensiveModeStanceParticleEffect(new Color(.5F, .5F, 0F, 0.0F)));
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
