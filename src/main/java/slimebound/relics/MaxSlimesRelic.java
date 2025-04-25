package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.util.TextureLoader;
import slimebound.SlimeboundMod;
import slimebound.actions.EnergyToCidAction;
import slimebound.actions.EnergyToPikeAction;
import slimebound.characters.SlimeboundCharacter;

public class MaxSlimesRelic extends CustomRelic {
    public static final String ID = "Slimebound:MaxSlimesRelic";
    public static final String IMG_PATH = "relics/slimeplushie.png";
    public static final String IMG_PATH_LARGE = "relics/slimeplushieLarge.png";
    public static final String OUTLINE_IMG_PATH = "relics/slimeplushieOutline.png";
    private static final int HP_PER_CARD = 1;

    public MaxSlimesRelic() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.UNCOMMON, LandingSound.FLAT);
        this.largeImg = TextureLoader.getTexture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH_LARGE));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw() {
        this.flash();
        addToBot(new EnergyToCidAction(1));
        addToBot(new EnergyToPikeAction(2));
    }

    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlimeboundCharacter;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MaxSlimesRelic();
    }

}