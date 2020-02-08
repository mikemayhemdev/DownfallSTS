package charbosses.cards;

import charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public abstract class AbstractBossDeckArchetype {
    public String ID;
    public ArrayList<AbstractBossCard> allCards;
    public ArrayList<AbstractCharbossRelic> synergyRelics;

    public AbstractBossDeckArchetype(String id) {
        this.ID = id;
        this.allCards = new ArrayList<AbstractBossCard>();
        this.synergyRelics = new ArrayList<AbstractCharbossRelic>();
    }

    private static AbstractBossCard getRandomCardFromSource(ArrayList<AbstractBossCard> source) {
        return (AbstractBossCard) source.get(AbstractDungeon.monsterRng.random(source.size() - 1)).makeCopy();
    }

    public abstract ArrayList<AbstractBossCard> buildCardList();

    public AbstractBossCard getRandomCard() {
        return getRandomCardFromSource(this.allCards);
    }

    public AbstractBossCard getRandomCard(ArrayList<AbstractBossCard> cards) {
        return this.getRandomCard(cards, this.allCards);
    }

    public AbstractBossCard getRandomCard(ArrayList<AbstractBossCard> cards, ArrayList<AbstractBossCard> source) {
        AbstractBossCard c = getRandomCardFromSource(source);
        if (c.limit <= cards.size()) {
            int count = 0;
            for (AbstractBossCard i : cards) {
                if (i.cardID == c.cardID) {
                    count++;
                }
            }
            if (count >= c.limit) {
                return this.getRandomCard(cards, source);
            } else {
                return c;
            }
        } else {
            return c;
        }
    }

    public AbstractBossCard getRandomCard(ArrayList<AbstractBossCard> cards, ArrayList<AbstractBossCard> source, CardRarity rarity) {
        return this.getRandomCard(cards, source, rarity, false);
    }

    public AbstractBossCard getRandomCard(ArrayList<AbstractBossCard> cards, CardRarity rarity) {
        return this.getRandomCard(cards, this.allCards, rarity, false);
    }

    public AbstractBossCard getRandomCard(ArrayList<AbstractBossCard> cards, CardRarity rarity, boolean blacklist) {
        return this.getRandomCard(cards, this.allCards, rarity, blacklist);
    }

    public AbstractBossCard getRandomCard(ArrayList<AbstractBossCard> cards, ArrayList<AbstractBossCard> source, CardRarity rarity, boolean blacklist) {
        AbstractBossCard c = getRandomCardFromSource(source);
        if ((c.rarity == rarity) == blacklist) {
            return this.getRandomCard(cards, source, rarity, blacklist);
        }
        if (c.limit <= cards.size()) {
            int count = 0;
            for (AbstractBossCard i : cards) {
                if (i.cardID == c.cardID) {
                    count++;
                }
            }
            if (count >= c.limit) {
                return this.getRandomCard(cards, source);
            } else {
                return c;
            }
        } else {
            return c;
        }
    }
}
