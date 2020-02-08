package guardian.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import guardian.GuardianMod;
import guardian.patches.RewardItemTypePatch;

public class GemReward extends CustomReward {

    public static final String[] TEXT;
    private static final Texture TEXTURE = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/gemreward.png"));

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;
    }

    public GemReward() {
        super(TEXTURE, TEXT[2], RewardItemTypePatch.GEM);
        GuardianMod.logger.info("New Gem Reward created, " + GuardianMod.getRewardGemCards(true).size() + "cards");
        this.cards = GuardianMod.getRewardGemCards(true);
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