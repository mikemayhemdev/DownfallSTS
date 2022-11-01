package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_Purifier extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("Purifier");
    private static final RelicTier tier = RelicTier.SPECIAL;
    private static final LandingSound sound = LandingSound.MAGICAL;
    private String cardName;

    public CBR_Purifier() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/purifier.png")));
        this.largeImg = null;
    }

    @Override
    public String getUpdatedDescription() {
        return this.cardName + this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Purifier();
    }
}
