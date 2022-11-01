package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_Serpent extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("Serpent");
    private static final RelicTier tier = RelicTier.SPECIAL;
    private static final LandingSound sound = LandingSound.MAGICAL;
    private String addedName;

    public CBR_Serpent() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/serpent.png")));
        this.largeImg = null;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Serpent();
    }
}
