package collector;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import java.util.ArrayList;

public class CollectorCollection implements CustomSavable<ArrayList<AbstractCard>> {
    public static CardGroup collection;
    public static CardGroup combatCollection;

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

    @Override
    public ArrayList<AbstractCard> onSave() {
        return collection.group;
    }

    @Override
    public void onLoad(ArrayList<AbstractCard> abstractCards) {
        for (AbstractCard c : abstractCards) {
            collection.addToTop(c);
        }
    }
}
