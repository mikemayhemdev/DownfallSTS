package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_TombOfRedMask extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("TombRedMask");
    private static final RelicTier tier = RelicTier.SPECIAL;
    private static final LandingSound sound = LandingSound.MAGICAL;
    private String cardName;

    public CBR_TombOfRedMask() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/redmasktomb.png")));
        this.largeImg = null;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_TombOfRedMask();
    }
}
