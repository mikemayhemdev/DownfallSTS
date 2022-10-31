package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class BerserkersGuideToSlaughter extends CustomRelic {

    public static final String ID = ChampMod.makeID("BerserkersGuideToSlaughter");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BerserkersGuide.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BerserkersGuide.png"));

    public BerserkersGuideToSlaughter() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();

        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, 3), 3));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
