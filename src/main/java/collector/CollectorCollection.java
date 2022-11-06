package collector;

import basemod.abstracts.CustomSavable;
import collector.util.CollectionReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.HashMap;

public class CollectorCollection implements CustomSavable<ArrayList<AbstractCard>> {
    public static CardGroup collection;
    public static CardGroup combatCollection;
    public static ArrayList<AbstractCard> SavedCollection = new ArrayList<>();
    public static HashMap<String, AbstractCard> cardsList;

    public static void init() {
        cardsList = new HashMap<>();
        collection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        combatCollection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    }

    public static void atBattleStart() {
        combatCollection.clear();
        for (AbstractCard q : collection.group) {
            combatCollection.addToTop(q.makeSameInstanceOf());
            System.out.println(q.name);
        }

        CollectionReward.collectPool.clear();
    }

    public static void atBattleEnd() {
        combatCollection.clear();
        if (!CollectionReward.collectPool.isEmpty()) {
            AbstractDungeon.getCurrRoom().rewards.add(new CollectionReward());
        }
    }

    public static void GetCollectible(AbstractMonster collectedMonster) {
        if (cardsList.containsKey(collectedMonster.id)) {
            AbstractCard NewCollectible = cardsList.get(collectedMonster.id).makeStatEquivalentCopy();
            CollectionReward.collectPool.add(NewCollectible);
        }
    }

    @Override
    public ArrayList<AbstractCard> onSave() {
        return SavedCollection = collection.group;
    }

    @Override
    public void onLoad(ArrayList<AbstractCard> abstractCards) {
        for (AbstractCard c : SavedCollection) {
            collection.addToTop(c);
        }
    }
}
