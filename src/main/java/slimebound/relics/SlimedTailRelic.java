package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnChannelRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.util.TextureLoader;

import slimebound.characters.SlimeboundCharacter;
import slimebound.orbs.SpawnedSlime;
import slimebound.powers.SlimeSacrificePower;

public class SlimedTailRelic extends CustomRelic {
    public static final String ID = "Slimebound:SlimedTailRelic";
    public static final String IMG_PATH = "relics/slimedTail.png";
    public static final String IMG_PATH_LARGE = "relics/slimedTailLarge.png";
    public static final String OUTLINE_IMG_PATH = "relics/slimedTailOutline.png";
    private static final int BLOCK_PER_TRIGGER = 2;

    public SlimedTailRelic() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.RARE, LandingSound.SOLID);
        this.largeImg = TextureLoader.getTexture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH_LARGE));

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + BLOCK_PER_TRIGGER + this.DESCRIPTIONS[1];
    }

    @Override
    public void onTrigger() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new GainBlockAction(AbstractDungeon.player, BLOCK_PER_TRIGGER));
    }
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlimeboundCharacter;
    }


    @Override
    public AbstractRelic makeCopy() {
        return new SlimedTailRelic();
    }

}