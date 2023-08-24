package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hermit.HermitMod;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class BloodyTooth extends CustomRelic {
    public static final String ID = HermitMod.makeID("BloodyTooth");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bloody_tooth.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bloody_tooth_outline.png"));

    public BloodyTooth() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    public void onVictory(){
        if ((AbstractDungeon.getCurrRoom()).eliteTrigger)
        {
            this.flash();
            this.counter++;
        }
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    public void atBattleStart() {
        if (this.counter > 0) {
            this.flash();
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.counter), this.counter));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
