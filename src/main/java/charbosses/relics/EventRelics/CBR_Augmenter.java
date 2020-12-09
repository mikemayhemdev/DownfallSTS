package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_Augmenter extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("Augmenter");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String addedName;
    private String removedName;
    private String addedName2;
    private int descInt = 0;
    private String removedName2;

    public CBR_Augmenter(int choiceIndex) {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/augmenter.png")));
        this.descInt = choiceIndex;
        this.largeImg = null;

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[descInt];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Augmenter(0);
    }
}
