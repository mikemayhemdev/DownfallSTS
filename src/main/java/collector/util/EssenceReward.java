package collector.util;

import basemod.abstracts.CustomReward;
import collector.patches.EssencePatches.TopPanelEssence;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import downfall.patches.RewardItemTypeEnumPatch;

import static collector.CollectorMod.makeID;

public class EssenceReward extends CustomReward {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("EssenceReward"));
    private final int amount;

    public EssenceReward(int amount) {
        super(TopPanelEssence.ICON, uiStrings.TEXT[0] + amount + uiStrings.TEXT[1], RewardItemTypeEnumPatch.COLLECTOR_ESSENCE);
        this.amount = amount;
    }

    @Override
    public boolean claimReward() {
        EssenceSystem.changeEssence(amount);
        return true;
    }
}