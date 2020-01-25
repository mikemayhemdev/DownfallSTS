package guardian.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import guardian.GuardianMod;
import guardian.patches.RewardItemTypePatch;

public class GemRewardAllRarities extends CustomReward {

    private static final Texture TEXTURE = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/gemreward.png"));
    public static final String[] TEXT;

    public GemRewardAllRarities() {
        super(TEXTURE, TEXT[6], RewardItemTypePatch.GEMALLRARITIES);
        GuardianMod.logger.info("New Gem Reward created, " + GuardianMod.getRewardGemCards(false).size() + "cards");
        this.cards = GuardianMod.getRewardGemCards(false);
    }

    @Override
    public boolean claimReward() {
        if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[1]);
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;
    }
}