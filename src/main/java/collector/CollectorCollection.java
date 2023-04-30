package collector;

import collector.cards.collectibles.LuckyWick;
import collector.util.CollectionReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.HashMap;

public class CollectorCollection {
    public static CardGroup collection;
    public static CardGroup combatCollection;
    private static HashMap<String, String> collectionPool;
    private static ArrayList<String> collectedCardsThisCombat = new ArrayList<>();

    static {
        collectionPool = new HashMap<>();
        //TODO: Populate Collection Pool
    }

    public static void init() {
        collection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (int i = 0; i < 3; i++)
            collection.addToRandomSpot(new LuckyWick());

        combatCollection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    }

    public static void atBattleStart() {
        combatCollection.clear();
        for (AbstractCard q : collection.group) {
            combatCollection.addToTop(q.makeSameInstanceOf());
        }
    }

    public static void atBattleEnd() {
        combatCollection.clear();

        if (!collectedCardsThisCombat.isEmpty())
            AbstractDungeon.getCurrRoom().rewards.add(new CollectionReward(collectedCardsThisCombat));

        collectedCardsThisCombat.clear();
    }

    //TODO: Save/load
//    public ArrayList<AbstractCard> onSave() {
//        return collection.group;
//    }
//
//    public void onLoad(ArrayList<AbstractCard> abstractCards) {
//        for (AbstractCard c : abstractCards) {
//            collection.addToTop(c);
//        }
//    }

    public static void collect(AbstractMonster m) {
        if (collectionPool.containsKey(m.id)) {
            collectedCardsThisCombat.add(collectionPool.get(m.id));
        } else {
            collectedCardsThisCombat.add(Madness.ID);
        }
    }
}
