package theHexaghost.util;

import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import downfall.patches.RewardItemTypeEnumPatch;
import downfall.util.TextureLoader;
import theHexaghost.HexaMod;
import theHexaghost.cards.seals.AbstractSealCard;
import theHexaghost.cards.seals.FifthSeal;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class SealSealReward extends CustomReward {
    public static final String ID = HexaMod.makeID("SealSealReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public SealSealReward() {
        super(TextureLoader.getTexture("downfallResources/images/rewards/placeholder.png"), TEXT[0], RewardItemTypeEnumPatch.SEALCARD);
        cards.clear();
        cards.addAll(getCards());
    }

    public static ArrayList<AbstractCard> getCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        while (cardsList.size() < 3) {
            AbstractCard q = getNonSixthSeal();
            if (!cardListDuplicate(cardsList, q)) {
                cardsList.add(q);
            }
        }
        return cardsList;
    }


    public static AbstractCard getNonSixthSeal() {
        ArrayList<AbstractCard> list = new ArrayList<>();// 1201
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q instanceof AbstractSealCard && !q.cardID.equals(FifthSeal.ID)) {
                list.add(q.makeCopy());
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