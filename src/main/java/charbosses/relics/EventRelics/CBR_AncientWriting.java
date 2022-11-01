package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_AncientWriting extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("AncientWriting");
    private static final RelicTier tier = RelicTier.SPECIAL;
    private static final LandingSound sound = LandingSound.MAGICAL;
    private int count;

    public CBR_AncientWriting() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/backtobasics.png")));
        this.largeImg = null;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_AncientWriting();
    }
}
