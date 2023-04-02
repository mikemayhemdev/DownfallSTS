package guardian.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.TextureLoader;
import guardian.GuardianMod;
import guardian.cards.*;
import guardian.patches.RewardItemTypePatch;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class GemReward extends CustomReward {

    public static final String[] TEXT;
    private static final Texture TEXTURE = TextureLoader.getTexture(GuardianMod.getResourcePath("ui/gemreward.png"));

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;
    }

    public GemReward() {
        super(TEXTURE, TEXT[2], RewardItemTypePatch.GEM);
//        GuardianMod.logger.info("New Gem Reward created, " + GuardianMod.getRewardGemCards(true).size() + "cards");
        this.cards.clear();
        this.cards = GemReward.getRewardGemCards(true,3);
    }

    @Override
    public boolean claimReward() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[1]);
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }


    public static ArrayList<AbstractCard> getRewardGemCards(boolean onlyCommon, int count) {
        ArrayList<String> allGemCards = new ArrayList<>();
        ArrayList<AbstractCard> rewardGemCards = new ArrayList<>();

        allGemCards.add("RED");
        allGemCards.add("GREEN");
        allGemCards.add("LIGHTBLUE");
        if (!onlyCommon) allGemCards.add("ORANGE");
        if (!onlyCommon) allGemCards.add("CYAN");
        if (!onlyCommon) allGemCards.add("WHITE");
        allGemCards.add("BLUE");
        if (!onlyCommon) allGemCards.add("CRIMSON");
        if (!onlyCommon) allGemCards.add("FRAGMENTED");
        if (!onlyCommon) allGemCards.add("PURPLE");
        if (!onlyCommon) allGemCards.add("SYNTHETIC");
        if (!onlyCommon) allGemCards.add("YELLOW");

        int rando;
        String ID;
        for (int i = 0; i < count; i++) {
            rando = cardRandomRng.random(0, allGemCards.size() - 1);
            ID = allGemCards.get(rando);
            switch (ID) {
                case "RED":
                    rewardGemCards.add(new Gem_Red());
                    break;
                case "GREEN":
                    rewardGemCards.add(new Gem_Green());
                    break;
                case "LIGHTBLUE":
                    rewardGemCards.add(new Gem_Lightblue());
                    break;
                case "ORANGE":
                    rewardGemCards.add(new Gem_Orange());
                    break;
                case "CYAN":
                    rewardGemCards.add(new Gem_Cyan());
                    break;
                case "WHITE":
                    rewardGemCards.add(new Gem_White());
                    break;
                case "BLUE":
                    rewardGemCards.add(new Gem_Blue());
                    break;
                case "CRIMSON":
                    rewardGemCards.add(new Gem_Crimson());
                    break;
                case "FRAGMENTED":
                    rewardGemCards.add(new Gem_Fragmented());
                    break;
                case "PURPLE":
                    rewardGemCards.add(new Gem_Purple());
                    break;
                case "SYNTHETIC":
                    rewardGemCards.add(new Gem_Synthetic());
                    break;
                case "YELLOW":
                    rewardGemCards.add(new Gem_Yellow());
                    break;
            }
            allGemCards.remove(rando);
        }

        return rewardGemCards;
    }

}