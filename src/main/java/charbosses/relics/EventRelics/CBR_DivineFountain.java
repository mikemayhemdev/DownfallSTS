package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_DivineFountain extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("DivineFountain");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private int numCurses;

    public CBR_DivineFountain() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/divinefountain.png")));
        this.largeImg = null;
    }

    @Override
    public String getUpdatedDescription() {
        return this.numCurses + this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DivineFountain();
    }
}
