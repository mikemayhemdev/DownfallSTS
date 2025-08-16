package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import static champ.ChampMod.*;

public class BerserkersGuideToSlaughter extends CustomRelic {

    public static final String ID = ChampMod.makeID("BerserkersGuideToSlaughter");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BerserkersGuide.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BerserkersGuide.png"));

    public BerserkersGuideToSlaughter() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    //vigor gain per turn
    private static final int AMOUNT = 3;

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        vigor(AMOUNT);
        //addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, 3), 3));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}
