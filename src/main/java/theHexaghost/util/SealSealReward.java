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

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRng;

public class SealSealReward extends CustomReward {
    public static final String ID = HexaMod.makeID("SealSealReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public SealSealReward() {
        super(TextureLoader.getTexture("downfallResources/images/rewards/seal_reward.png"), TEXT[0], RewardItemTypeEnumPatch.SEALCARD);
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


    public void generate_reward_cards(){
        this.cards.clear();
        this.cards.addAll(getCards());
    }

    public static AbstractCard getNonSixthSeal() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q instanceof AbstractSealCard && !q.cardID.equals(FifthSeal.ID)) {
                list.add(q.makeCopy());
            }
        }
        return list.get(cardRng.random(list.size() - 1));
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
            AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[1]);
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }
}