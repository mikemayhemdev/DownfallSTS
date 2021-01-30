package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import slimebound.characters.SlimeboundCharacter;

public class TarBlob extends CustomRelic {
    public static final String ID = "Slimebound:TarBlob";
    public static final String IMG_PATH = "relics/tarblob.png";
    public static final String OUTLINE_IMG_PATH = "relics/tarblobOutline.png";
    private static final int HP_PER_CARD = 1;

    public TarBlob() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }

    public void atBattleStartPreDraw() {
        this.flash();
        com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction(1));
    }


    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlimeboundCharacter;
    }

    public AbstractRelic makeCopy() {
        return new TarBlob();
    }

}