package charbosses.relics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;


public class CBR_NeowsBlessing extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("NeowBlessing");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    public String relicName = "";
    private int HP = 0;

    public CBR_NeowsBlessing() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/blessing.png")));
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
        if (this.owner != null) {
            return this.DESCRIPTIONS[0] + this.owner.energyString + DESCRIPTIONS[1];
        }
        return this.DESCRIPTIONS[0] + "[E]" + DESCRIPTIONS[1];
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_NeowsBlessing();
    }
}
