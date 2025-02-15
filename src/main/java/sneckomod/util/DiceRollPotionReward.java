package sneckomod.util;

import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import downfall.downfallMod;
import downfall.patches.RewardItemTypeEnumPatch;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER;

public class DiceRollPotionReward extends CustomReward {
    public static final String ID = downfallMod.makeID("DiceRollPotionReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;


    public DiceRollPotionReward() {
        super(TextureLoader.getTexture("downfallResources/images/rewards/placeholder.png"), TEXT[0] + TEXT[1], RewardItemTypeEnumPatch.COLORFULCARD);
        cards.clear();
        cards.addAll(getUpgradedUncommonCards());
    }

    private ArrayList<AbstractCard> getUpgradedUncommonCards() {
        ArrayList<AbstractCard> selectedCards = new ArrayList<>();

        while (selectedCards.size() < 3) {
            AbstractCard card = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity == AbstractCard.CardRarity.UNCOMMON);
            card.upgrade();
            if (!isDuplicate(selectedCards, card)) {
                selectedCards.add(card);
            }
        }

        // Fallback in case there aren't enough unique uncommon cards of the color
        return selectedCards.isEmpty() ? getDebugFallbackCards() : selectedCards;
    }

    private ArrayList<AbstractCard> getDebugFallbackCards() {
        ArrayList<AbstractCard> debugCards = new ArrayList<>();
        for (AbstractCard card : CardLibrary.getAllCards()) {
            debugCards.add(card.makeCopy());
        }
        return debugCards;
    }

    private boolean isDuplicate(ArrayList<AbstractCard> cardList, AbstractCard card) {
        return cardList.stream().anyMatch(c -> c.cardID.equals(card.cardID));
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
