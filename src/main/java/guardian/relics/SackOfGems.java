package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import guardian.GuardianMod;
import guardian.rewards.GemReward;
import guardian.rewards.GemRewardAllRarities;
import sneckomod.util.ColorfulRareReward;

import java.util.ArrayList;

public class SackOfGems extends CustomRelic {
    public static final String ID = "Guardian:SackOfGems";
    public static final String IMG_PATH = "relics/sackOfgems.png";
    public static final String OUTLINE_IMG_PATH = "relics/sackOfgemsOutline.png";
    private static final int HP_PER_CARD = 1;

    public SackOfGems() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        for (int i = 0; i < 5; i++) {
            AbstractDungeon.getCurrRoom().rewards.add(new GemReward());
        }
        AbstractDungeon.combatRewardScreen.rewards.remove(AbstractDungeon.combatRewardScreen.rewards.size()-1);
        AbstractDungeon.combatRewardScreen.open();
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;

    }

    @Override
    public AbstractRelic makeCopy() {
        return new SackOfGems();
    }
}