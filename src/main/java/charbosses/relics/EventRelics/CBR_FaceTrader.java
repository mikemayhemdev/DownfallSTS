package charbosses.relics.EventRelics;

import charbosses.relics.*;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_FaceTrader extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("FaceTrader");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;

    public String cardName = "";

    public CBR_FaceTrader() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/facetrader.png")));
        this.largeImg = null;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_FaceTrader();
    }
}
