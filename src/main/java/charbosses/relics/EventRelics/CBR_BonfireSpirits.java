package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_BonfireSpirits extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("BonfireSpirits");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    public String cardName = "";
    private int descInt = 0;

    public CBR_BonfireSpirits() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/bonfirespirits.png")));
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
    public void onEquip() {
        AbstractCharBoss.boss.increaseMaxHp(10, true);
    }

    @Override
    public String getUpdatedDescription() {
       return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BonfireSpirits();
    }
}
