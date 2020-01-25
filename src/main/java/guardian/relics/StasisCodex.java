package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.CodexAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import guardian.GuardianMod;
import guardian.actions.StasisCodexAction;

public class StasisCodex extends CustomRelic {
    public static final String ID = "Guardian:StasisCodex";
    public static final String IMG_PATH = "relics/constructCodex.png";
    public static final String OUTLINE_IMG_PATH = "relics/constructCodexOutline.png";
    public static final String LARGE_IMG_PATH = "relics/constructCodexLarge.png";
    private static final int HP_PER_CARD = 1;

    public StasisCodex() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.UNCOMMON, LandingSound.FLAT);
        this.largeImg = ImageMaster.loadImage(GuardianMod.getResourcePath(LARGE_IMG_PATH));
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        if (!GuardianMod.isStasisOrbInPlay()){

            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new StasisCodexAction());
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public AbstractRelic makeCopy() {
        return new StasisCodex();
    }
}