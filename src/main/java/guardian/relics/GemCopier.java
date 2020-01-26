package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import guardian.GuardianMod;
import guardian.actions.StasisCodexAction;

public class GemCopier extends CustomRelic {
    public static final String ID = "Guardian:GemCopier";
    public static final String IMG_PATH = "relics/gemTumbler.png";
    public static final String OUTLINE_IMG_PATH = "relics/gemTumblerOutline.png";
    public static final String LARGE_IMG_PATH = "relics/gemTumblerOutlineLarge.png";
    private static final int HP_PER_CARD = 1;

    public GemCopier() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.SHOP, LandingSound.FLAT);
        this.largeImg = ImageMaster.loadImage(GuardianMod.getResourcePath(LARGE_IMG_PATH));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (c.hasTag(GuardianMod.GEM)){
            AbstractCard gemCard = c.makeStatEquivalentCopy();
            AbstractDungeon.actionManager.addToBottom(new QueueCardAction(gemCard, m));
            this.flash();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GemCopier();
    }
}