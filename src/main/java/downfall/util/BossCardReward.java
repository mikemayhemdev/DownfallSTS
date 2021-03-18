package downfall.util;

import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import downfall.downfallMod;
import downfall.patches.RewardItemTypeEnumPatch;
import expansioncontent.expansionContentMod;
import expansioncontent.patches.ShopBossPatch;

import java.util.ArrayList;

public class BossCardReward extends CustomReward {
    public static final String ID = downfallMod.makeID("BossCardReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public BossCardReward() {
        super(TextureLoader.getTexture("downfallResources/images/rewards/placeholder.png"), TEXT[0], RewardItemTypeEnumPatch.BOSSCARD);
        cards.clear();
        cards.addAll(getCards());
    }

    public static ArrayList<AbstractCard> getCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        while (cardsList.size() < 3) {
            AbstractCard q = getBossCard();
            if (!cardListDuplicate(cardsList, q) && q.rarity != AbstractCard.CardRarity.SPECIAL) {
                cardsList.add(q.makeCopy());
            }
        }
        return cardsList;
    }

    public static AbstractCard getBossCard() {
        ArrayList<AbstractCard> potentialCardsList = new ArrayList<>();
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q.hasTag(expansionContentMod.STUDY) && ShopBossPatch.okayToSpawn(q)) {
                potentialCardsList.add(q);
            }
        }
        return potentialCardsList.get(AbstractDungeon.merchantRng.random(0, potentialCardsList.size() - 1));
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