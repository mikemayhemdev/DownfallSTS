package sneckomod.util;

import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.patches.RewardItemTypeEnumPatch;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class UpgradedUnknownReward extends CustomReward {
    public static final String ID = SneckoMod.makeID("UpgradedUnknownReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public UpgradedUnknownReward() {
        super(TextureLoader.getTexture("downfallResources/images/rewards/placeholder.png"), TEXT[0], RewardItemTypeEnumPatch.UPGRADEDUNKNOWNCARD);
        cards.clear();
        cards.addAll(getCards());
    }

    public static ArrayList<AbstractCard> getCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        while (cardsList.size() < 3) {
            AbstractCard q = getUnknownUpgradedCard();
            if (!cardListDuplicate(cardsList, q)) {
                AbstractCard r = q.makeCopy();
                r.upgrade();
                cardsList.add(r);
            }
        }
        return cardsList;
    }


    public static AbstractCard getUnknownUpgradedCard() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.commonCardPool.group) {
            if (c instanceof AbstractUnknownCard) {
                AbstractCard q = c.makeCopy();
                q.upgrade();
                list.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.uncommonCardPool.group) {
            if (c instanceof AbstractUnknownCard) {
                AbstractCard q = c.makeCopy();
                q.upgrade();
                list.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.rareCardPool.group) {
            if (c instanceof AbstractUnknownCard) {
                AbstractCard q = c.makeCopy();
                q.upgrade();
                list.add(c);
            }
        }
        return list.get(cardRandomRng.random(list.size() - 1));// 1217
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