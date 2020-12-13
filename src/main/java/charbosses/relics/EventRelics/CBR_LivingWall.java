package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_LivingWall extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("LivingWall");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String cardName;
    private String cardName2;

    public CBR_LivingWall() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/livingwall.png")));
        this.largeImg = null;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_LivingWall();
    }
}
