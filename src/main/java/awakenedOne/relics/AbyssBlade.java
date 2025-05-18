package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;

public class AbyssBlade extends CustomRelic implements OnLoseEnergyRelic {

    public static final String ID = AwakenedOneMod.makeID("AbyssBlade");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("AbyssBlade.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("AbyssBlade.png"));

    public AbyssBlade() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }


    @Override
    public void atBattleStart() {
        this.counter = -1;
    }

    @Override
    public void atTurnStart() {
        this.counter = -1;
        beginLongPulse();
    }

    @Override
    public void onVictory(){
        this.counter = -1;
        stopPulse();
    }

    @Override
    public void LoseEnergyAction(int e) {
        if (this.counter != 1) {
            flash();
            stopPulse();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(1));
            addToBot(new DrawCardAction(AbstractDungeon.player, 1));
            this.counter = 1;
        }
    }



    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}