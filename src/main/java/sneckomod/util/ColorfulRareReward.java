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

public class ColorfulRareReward extends CustomReward {
    public static final String ID = downfallMod.makeID("ColorfulUncommonCardReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    private final AbstractCard.CardColor myColor;

    public ColorfulRareReward(AbstractCard.CardColor myColor) {
        super(TextureLoader.getTexture("downfallResources/images/rewards/placeholder.png"),
                TEXT[0] + SneckoMod.getClassFromColor(myColor) + TEXT[1],
                RewardItemTypeEnumPatch.COLORFULCARD);
        this.myColor = myColor;
        cards.clear();
        cards.addAll(getUpgradedUncommonCards());
    }

    private ArrayList<AbstractCard> getUpgradedUncommonCards() {
        ArrayList<AbstractCard> selectedCards = new ArrayList<>();
        ArrayList<AbstractCard> availableCards = new ArrayList<>();

        // Filter for colored uncommon cards that are not banned for Snecko
        for (AbstractCard card : CardLibrary.getAllCards()) {
            if (card.color == myColor &&
                    card.rarity == AbstractCard.CardRarity.RARE &&
                    !card.tags.contains(SneckoMod.BANNEDFORSNECKO)) {
                availableCards.add(card);
            }
        }

        // If we have enough cards, add three unique upgraded ones to the list
        while (selectedCards.size() < 3 && !availableCards.isEmpty()) {
            AbstractCard card = availableCards.get(AbstractDungeon.relicRng.random(0, availableCards.size() - 1)).makeCopy();
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
