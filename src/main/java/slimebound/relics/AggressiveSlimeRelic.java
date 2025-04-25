package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.util.TextureLoader;

import slimebound.cards.SlimeBrawl;
import slimebound.cards.SplitSpecialist;
import slimebound.characters.SlimeboundCharacter;

public class AggressiveSlimeRelic extends CustomRelic {
    public static final String ID = "Slimebound:AggressiveSlimeRelic";
    public static final String IMG_PATH = "relics/minion.png";
    public static final String IMG_PATH_LARGE = "relics/minionLarge.png";
    public static final String OUTLINE_IMG_PATH = "relics/minionOutline.png";
    private static final int HP_PER_CARD = 1;

    public AggressiveSlimeRelic() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.COMMON, LandingSound.FLAT);
        this.largeImg = TextureLoader.getTexture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH_LARGE));

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw() {
        this.flash();
        AbstractCard c;
        if (AbstractDungeon.cardRandomRng.randomBoolean()){
            c = new SlimeBrawl();
        } else {
            c = new SplitSpecialist();
        }
        c.upgrade();
        addToBot(new MakeTempCardInHandAction(c));

       // AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.AttackSlime(), false, true));

    }

    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlimeboundCharacter;
    }


    @Override
    public AbstractRelic makeCopy() {
        return new AggressiveSlimeRelic();
    }

}