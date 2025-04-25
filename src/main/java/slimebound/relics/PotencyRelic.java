package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.util.TextureLoader;
import slimebound.actions.BuffCidStrengthAction;
import slimebound.actions.BuffPikeStrengthAction;

import slimebound.characters.SlimeboundCharacter;
import slimebound.powers.PotencyPower;

public class PotencyRelic extends CustomRelic {
    public static final String ID = "Slimebound:PotencyRelic";
    public static final String IMG_PATH = "relics/oozeStone.png";
    public static final String IMG_PATH_LARGE = "relics/oozeStoneLarge.png";
    public static final String OUTLINE_IMG_PATH = "relics/oozeStoneOutline.png";

    public PotencyRelic() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.COMMON, LandingSound.MAGICAL);
        this.largeImg = TextureLoader.getTexture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH_LARGE));

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new BuffCidStrengthAction(1));
        addToBot(new BuffPikeStrengthAction(1));

        //addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PotencyPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
    }

    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlimeboundCharacter;
    }


    @Override
    public AbstractRelic makeCopy() {
        return new PotencyRelic();
    }

}