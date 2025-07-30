package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.BurningStudy;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;
import static awakenedOne.util.Wiz.atb;
import static downfall.downfallMod.DeterministicConjure;

public class RippedDoll extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("RippedDoll");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("RippedDoll.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("RippedDoll.png"));

    //Ripped Doll

    public RippedDoll() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    public void onEquip() {
        this.counter = -1;
    }

    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        if (this.counter < 2) {
            this.counter++;
            flash();
            atb(new ConjureAction(false, false));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + 2 + DESCRIPTIONS[1];
    }

}
