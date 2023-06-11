package collector;

import collector.cards.collectibles.LuckyWick;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.HashMap;

import static collector.util.Wiz.makeInHand;

public class CollectorCollection {
    public static CardGroup collection;
    public static CardGroup combatCollection;
    public static HashMap<String, String> collectionPool;

    static {
        collectionPool = new HashMap<>();
        //TODO: Populate Collection Pool. Monster ID -> Card.
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
        AbstractCard tar;
        if (collectionPool.containsKey(m.id)) {
            tar = CardLibrary.getCard(collectionPool.get(m.id));
        } else {
            tar = new Madness();
        }
        collection.addToBottom(tar.makeCopy());
        makeInHand(tar.makeCopy());
    }
}
