package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_ShiningLight extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("ShiningLight");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String cardName;
    private String cardName2;

    public CBR_ShiningLight() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/shininglight.png")));
        this.largeImg = null;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.cardName + this.DESCRIPTIONS[1] + this.cardName2 +  this.DESCRIPTIONS[2];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ShiningLight();
    }
}
