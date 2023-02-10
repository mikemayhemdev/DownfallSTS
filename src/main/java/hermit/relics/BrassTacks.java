package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import hermit.HermitMod;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class BrassTacks extends CustomRelic {
    public static final String ID = HermitMod.makeID("BrassTacks");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("brass_tacks.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("brass_tacks_outline.png"));

    public BrassTacks() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MetallicizePower(AbstractDungeon.player, 2), 2));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
