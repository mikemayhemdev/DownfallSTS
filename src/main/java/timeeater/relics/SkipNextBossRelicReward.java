package timeeater.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import timeeater.TimeEaterMod;
import timeeater.util.TexLoader;

import static timeeater.TimeEaterMod.makeRelicOutlinePath;
import static timeeater.TimeEaterMod.makeRelicPath;


public class SkipNextBossRelicReward extends CustomRelic {
    public static final String ID = TimeEaterMod.makeID("SkipNextBossRelicReward");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("Watch.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("Watch.png"));


    public SkipNextBossRelicReward() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);

    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    public AbstractRelic makeCopy() {
        return new SkipNextBossRelicReward();
    }

}

