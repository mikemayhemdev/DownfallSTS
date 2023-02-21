package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.HermitMod;
import hermit.powers.Concentration;
import hermit.powers.RyeStalkPower;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class Spyglass extends CustomRelic {

    // ID, images, text.
    public static final String ID = HermitMod.makeID("Spyglass");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("spyglass.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("spyglass.png"));

    public Spyglass() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }


    // Gain 1 Strength on on equip.
    /*
    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player, new Concentration(AbstractDungeon.player,-1)));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
     */


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
