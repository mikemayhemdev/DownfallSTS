package theHexaghost.util;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import downfall.patches.RewardItemTypeEnumPatch;
import downfall.util.TextureLoader;
import theHexaghost.HexaMod;
import theHexaghost.cards.seals.*;

import java.util.ArrayList;
import java.util.HashMap;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRng;

public class SealSealReward extends CustomReward {
    public static final String ID = HexaMod.makeID("SealSealReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public SealSealReward() {
        super(TextureLoader.getTexture("downfallResources/images/rewards/seal_reward.png"), TEXT[0], RewardItemTypeEnumPatch.SEALCARD);
    }

//    public static ArrayList<AbstractCard> getCards() {
//        ArrayList<AbstractCard> cardsList = new ArrayList<>();
//        while (cardsList.size() < 3) {
//            AbstractCard q = getNonSixthSeal();
//            if (!cardListDuplicate(cardsList, q)) {
//                cardsList.add(q);
//            }
//        }
//        return cardsList;
//    }

    public static AbstractSealCard newGetCcard(){
        int sum = 0;
        int progress = 0;
        for(int i = 1; i <= 6; i++){
            sum += HexaMod.seal_weight[i];
        }
        HashMap<Integer, AbstractSealCard> dic = new HashMap<>();
        dic.put(1, new FirstSeal());
        dic.put(2, new SecondSeal());
        dic.put(3, new ThirdSeal());
        dic.put(4, new FourthSeal());
        dic.put(5, new SixthSeal());
        dic.put(6, new FifthSeal());
        while(true) {
            int roll = cardRng.random(sum);
            System.out.println("Rolled on seal generation: " + roll);
            for (int i = 1; i <= 6; i++) {
                progress += HexaMod.seal_weight[i];
                if (roll <= progress) {
                    AbstractCard new_seal = dic.get(i);
                    float upgrade_chance = ReflectionHacks.getPrivateStatic(AbstractDungeon.class, "cardUpgradedChance" );
                    if (AbstractDungeon.cardRng.randomBoolean(upgrade_chance) && new_seal.canUpgrade()) {
                        new_seal.upgrade();
                    }
                    if(AbstractDungeon.player.hasRelic(FrozenEgg2.ID)){
                        new_seal.upgrade();
                    }
                    return dic.get(i);
                }
            }
        }
    }


    public void generate_reward_cards(){
        this.cards.clear();
//        this.cards.addAll(getCards());
        this.cards.add(newGetCcard());
    }


//    public static AbstractCard getNonSixthSeal() {
//        ArrayList<AbstractCard> list = new ArrayList<>();
//        for (AbstractCard q : CardLibrary.getAllCards()) {
//            if (q instanceof AbstractSealCard && !q.cardID.equals(FifthSeal.ID)) {
//                list.add(q.makeCopy());
//            }
//        }
//        return list.get(cardRng.random(list.size() - 1));
//    }

//    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
//        for (AbstractCard alreadyHave : cardsList) {
//            if (alreadyHave.cardID.equals(card.cardID)) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public boolean claimReward() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[1]);
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }
}