package collector;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.HashMap;

public class CollectorCollection {
    public static CardGroup collection;
    public static CardGroup combatCollection;
    public static HashMap<String, String> collectionPool;

    static {
        collectionPool = new HashMap<>();
        //TODO: Populate Collection Pool. Monster ID -> Card.
    }

    public static AbstractCard getCollectedCard(AbstractMonster m) {
        if (collectionPool.containsKey(m.id)) {
            return CardLibrary.getCopy(collectionPool.get(m.id));
        } else {
            return new Madness(); //TODO: Special card for modded enemies
        }
    }

    public static void init() {
        collection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
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
        collection.addToBottom(getCollectedCard(m));
        //TODO: Collected card flying into the top panel icon visual
    }
}
