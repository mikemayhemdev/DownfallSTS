package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_GoldenShrine extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("GoldenShrine");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;

    public String relicName = "";

    public CBR_GoldenShrine() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/goldenshrine.png")));
        this.largeImg = null;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.relicName + ".";
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_GoldenShrine();
    }
}
