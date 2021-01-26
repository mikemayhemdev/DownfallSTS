package sneckomod.util;

import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import downfall.downfallMod;
import downfall.patches.RewardItemTypeEnumPatch;
import downfall.util.TextureLoader;

import java.util.ArrayList;

public class ColorfulCardReward extends CustomReward {
    public static final String ID = downfallMod.makeID("ColorfulCardReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public AbstractCard.CardColor myColor;

    public ColorfulCardReward(AbstractCard.CardColor myColor) {
        super(TextureLoader.getTexture("downfallResources/images/rewards/placeholder.png"), TEXT[0], RewardItemTypeEnumPatch.COLORFULCARD);
        cards.clear();
        cards.addAll(getCards());
        this.myColor = myColor;
    }

    public ArrayList<AbstractCard> getCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        ArrayList<AbstractCard> listOfColoredCards = new ArrayList<>();
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q.color == myColor) {
                listOfColoredCards.add(q);
            }
        }
        while (cardsList.size() < 3) {
            AbstractCard q = listOfColoredCards.get(AbstractDungeon.cardRandomRng.random(0, listOfColoredCards.size() - 1));
            if (!cardListDuplicate(cardsList, q)) {
                cardsList.add(q);
            }
        }
        return cardsList;
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
            AbstractDungeon.cardRewardScreen.open(this.cards, this, "Pick a Card.");
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }
}