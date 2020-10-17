package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_Cleric extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("Cleric");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String cardName;

    public CBR_Cleric() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/cleric.png")));
        this.largeImg = null;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Cleric();
    }
}
