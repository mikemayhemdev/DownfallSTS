package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_GoldenIdolEvent extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("GoldenIdolEvent");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String addedName;

    public CBR_GoldenIdolEvent() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/goldenidol.png")));
        this.largeImg = null;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.addedName + ".";
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_GoldenIdolEvent();
    }
}
