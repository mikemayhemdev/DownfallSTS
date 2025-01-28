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

public class ColorfulCardReward extends CustomReward {
    public static final String ID = downfallMod.makeID("ColorfulCardReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public AbstractCard.CardColor myColor;

    public ColorfulCardReward(AbstractCard.CardColor myColor) {
        super(TextureLoader.getTexture("downfallResources/images/rewards/placeholder.png"), TEXT[0] + SneckoMod.getClassFromColor(myColor) + TEXT[1], RewardItemTypeEnumPatch.COLORFULCARD);
        cards.clear();
        this.myColor = myColor;
        cards.addAll(getCards());
    }

    public ArrayList<AbstractCard> getCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        ArrayList<AbstractCard> listOfColoredCards = new ArrayList<>();

        for (AbstractCard card : CardLibrary.getAllCards()) {
            if (card.color == myColor && card.rarity != AbstractCard.CardRarity.SPECIAL && !card.tags.contains(SneckoMod.BANNEDFORSNECKO)) {
                listOfColoredCards.add(card);
            }
        }

        if (!listOfColoredCards.isEmpty()) {
            while (cardsList.size() < 3) {
                AbstractCard card = getRandomCardByRarity(listOfColoredCards);
                if (!cardListDuplicate(cardsList, card)) {
                    cardsList.add(card.makeCopy());
                }
            }
            return cardsList;
        } else {

            ArrayList<AbstractCard> debug = new ArrayList<>();
            for (AbstractCard card : CardLibrary.getAllCards()) {
                debug.add(card.makeCopy());
            }
            return debug;
        }
    }

    private AbstractCard getRandomCardByRarity(ArrayList<AbstractCard> cardPool) {
        int roll = AbstractDungeon.cardRandomRng.random(0, 99); // 0-99 random number
        AbstractCard.CardRarity rarity;

        if (roll < 50) {
            rarity = AbstractCard.CardRarity.COMMON; // 50%
        } else if (roll < 90) {
            rarity = AbstractCard.CardRarity.UNCOMMON; // 40%
        } else {
            rarity = AbstractCard.CardRarity.RARE; // 10%
        }

        ArrayList<AbstractCard> filteredPool = new ArrayList<>();
        for (AbstractCard card : cardPool) {
            if (card.rarity == rarity) {
                filteredPool.add(card);
            }
        }

        if (filteredPool.isEmpty()) {
            return cardPool.get(AbstractDungeon.cardRandomRng.random(0, cardPool.size() - 1));
        }

        return filteredPool.get(AbstractDungeon.cardRandomRng.random(0, filteredPool.size() - 1));
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID)) {
                return true;
            }
        }
        return false;
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
