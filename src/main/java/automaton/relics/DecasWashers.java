package automaton.relics;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import downfall.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class DecasWashers extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("DecasWashers");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DecasWashers.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DecasWashers.png"));

    public DecasWashers() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    //How many turns does this relic work for?
    private static final int TURNS = 3;

    public void atBattleStart() {
        this.counter = 0;
        this.grayscale = false;
    }

    public void atTurnStartPostDraw() {
        if (!this.grayscale) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DrawCardAction(AbstractDungeon.player, 1));
            ++this.counter;
        }

        if (this.counter == TURNS) {
            this.counter = -1;
            this.grayscale = true;
        }
    }

    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + TURNS + DESCRIPTIONS[1];
    }

}
