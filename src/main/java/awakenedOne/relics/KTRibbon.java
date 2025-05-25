package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;

public class KTRibbon extends CustomRelic implements OnAwakenRelic {

    //White Ribbon

    //what is this, some kind of "daemon doll"????? a "constant companion"?

    private static final int AMOUNT = 4;

    public static final String ID = AwakenedOneMod.makeID("KTRibbon");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("KTRibbon.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("KTRibbon.png"));

    public KTRibbon() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
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

    public void onTrigger() {
        flash();
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new GainBlockAction(AbstractDungeon.player, AMOUNT));
        this.counter = 1;
    }

    //Check AwakenButton.java. I'm just using this override for convenience.
    @Override
    public void onAwaken(int amount) {
        if (this.counter == -1) {
            if (amount == 1) {
                stopPulse();
                this.counter = 1;
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}
