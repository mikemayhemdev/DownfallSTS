package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import slimebound.characters.SlimeboundCharacter;
import slimebound.powers.AcidTonguePowerUpgraded;

public class SlimedSkullRelic extends CustomRelic {
    public static final String ID = "Slimebound:SlimedSkullRelic";
    public static final String IMG_PATH = "relics/slimedSkull.png";
    public static final String IMG_PATH_LARGE = "relics/slimedSkullLarge.png";
    public static final String OUTLINE_IMG_PATH = "relics/slimedSkullOutline.png";
    private static final int HP_PER_CARD = 1;

    public SlimedSkullRelic() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.RARE, LandingSound.SOLID);
        this.largeImg = ImageMaster.loadImage(slimebound.SlimeboundMod.getResourcePath(IMG_PATH_LARGE));

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    public void atBattleStartPreDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AcidTonguePowerUpgraded(AbstractDungeon.player, AbstractDungeon.player, 1), 1));

    }

    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlimeboundCharacter;
    }


    @Override
    public AbstractRelic makeCopy() {
        return new SlimedSkullRelic();
    }

}