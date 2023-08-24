package guardian.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.patches.RewardItemTypeEnumPatch;
import downfall.util.TextureLoader;
import guardian.GuardianMod;

public class GemRewardAllRarities extends CustomReward {

    public static final String[] TEXT;
    private static final Texture TEXTURE = TextureLoader.getTexture(GuardianMod.getResourcePath("ui/gemreward.png"));

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;
    }

    public GemRewardAllRarities() {
        super(TEXTURE, TEXT[6], RewardItemTypeEnumPatch.GEMALLRARITIES);
        GuardianMod.logger.info("New Gem Reward created, " + GuardianMod.getRewardGemCards(false).size() + "cards");
        this.cards = GuardianMod.getRewardGemCards(false);
    }

    @Override
    public boolean claimReward() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[1]);
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }
}