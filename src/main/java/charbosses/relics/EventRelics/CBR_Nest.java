package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_Nest extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("Nest");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;

    public CBR_Nest() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/nest.png")));
        this.largeImg = null;
    }

    @Override
    public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0];
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Nest();
    }
}
