package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.EvolvePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class StickyStick extends CustomRelic {
    public static final String ID = "Slimebound:StickyStick";
    public static final String IMG_PATH = "relics/StickyStick.png";
    public static final String IMG_PATH_LARGE = "relics/StickyStickLarge.png";
    public static final String OUTLINE_IMG_PATH = "relics/StickyStickOutline.png";
    private static final int HP_PER_CARD = 1;

    public StickyStick() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.COMMON, LandingSound.SOLID);
        this.largeImg = ImageMaster.loadImage(slimebound.SlimeboundMod.getResourcePath(IMG_PATH_LARGE));

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw() {
        this.flash();// 24
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EvolvePower(AbstractDungeon.player, 1), 1));// 25
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 31
    }// 32

    @Override
    public AbstractRelic makeCopy() {
        return new StickyStick();
    }

}