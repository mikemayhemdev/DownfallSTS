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

public class MomsKnife extends CustomRelic implements OnLoseEnergyRelic {

    //Butcher's Knife

    public static final String ID = AwakenedOneMod.makeID("MomsKnife");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("MomsKnife.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("MomsKnife.png"));

    public MomsKnife() {
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
        if (this.counter == -1) {
            flash();
            stopPulse();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

            this.counter = 1;
        }
    }



    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
