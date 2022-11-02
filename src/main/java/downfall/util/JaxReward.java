package downfall.util;

import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.colorless.JAX;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;
import downfall.patches.RewardItemTypeEnumPatch;

public class JaxReward extends CustomReward {
    public static final String ID = downfallMod.makeID("JaxReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("downfall:SpecificCardReward").TEXT;

    public JaxReward() {
        super(TextureLoader.getTexture("downfallResources/images/rewards/placeholder.png"), "ERROR", RewardItemTypeEnumPatch.JAXCARD);
        cards.clear();
        JAX jax = new JAX();
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            r.onPreviewObtainCard(jax);
        }
        cards.add(jax);
        this.text = TEXT[0] + cards.get(0).name + TEXT[1];
    }

    @Override
    public boolean claimReward() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[2]);
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }
}