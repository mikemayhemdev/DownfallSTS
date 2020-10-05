package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_Vampires extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("Vampires");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = AbstractRelic.LandingSound.MAGICAL;

    public int hpLoss;

    public CBR_Vampires() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/vampires.png")));
        this.largeImg = null;

    }


    @Override
    public void updateDescription(final AbstractPlayer.PlayerClass c) {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.hpLoss + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Vampires();
    }
}
