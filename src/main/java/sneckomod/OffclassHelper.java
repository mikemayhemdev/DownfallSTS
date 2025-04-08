package sneckomod;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import sneckomod.cards.unknowns.*;

import java.util.ArrayList;
import java.util.function.Predicate;

public class OffclassHelper {
    //I wanted to move Unknowns pools here as well, but it is too much to do in one update.

    private static ArrayList<AbstractCard> statuses = new ArrayList<>();
    private static ArrayList<String> offclassCommons = new ArrayList<>();
    private static ArrayList<String> offclassUncommons = new ArrayList<>();
    private static ArrayList<String> offclassRares = new ArrayList<>();

    public static void addToLists(AbstractUnknownCard c, ArrayList<Predicate<AbstractCard>> predList, ArrayList<ArrayList<String>> listList) {
        predList.add(c.myNeeds());
        if (c.myList() != null) {
            c.myList().clear();
            listList.add(c.myList());
        }
    }

    public static void updateAllUnknownReplacements() {
        // there are no unknown cards, removed the to-do here due to it having no reason to exist

        ArrayList<Predicate<AbstractCard>> predList = new ArrayList<>();
        ArrayList<ArrayList<String>> listList = new ArrayList<>();

        addToLists(new Unknown(), predList, listList); // 0
        addToLists(new Unknown0Cost(), predList, listList);
        addToLists(new Unknown1Cost(), predList, listList);
        addToLists(new Unknown2Cost(), predList, listList);
        addToLists(new Unknown3Cost(), predList, listList);
        addToLists(new UnknownBlock(), predList, listList); // 5
        addToLists(new UnknownCommonAttack(), predList, listList);
        addToLists(new UnknownCommonSkill(), predList, listList);
        addToLists(new UnknownDexterity(), predList, listList);
        addToLists(new UnknownExhaust(), predList, listList);
        addToLists(new UnknownRareAttack(), predList, listList); // 10
        addToLists(new UnknownRarePower(), predList, listList);
        addToLists(new UnknownRareSkill(), predList, listList);
        addToLists(new UnknownStrength(), predList, listList);
        addToLists(new UnknownStrike(), predList, listList);
        addToLists(new UnknownUncommonAttack(), predList, listList); // 15
        addToLists(new UnknownUncommonSkill(), predList, listList);
        addToLists(new UnknownUncommonPower(), predList, listList);
        addToLists(new UnknownVulnerable(), predList, listList);
        addToLists(new UnknownWeak(), predList, listList);
        addToLists(new UnknownX(), predList, listList);  // 20
        addToLists(new UnknownDraw(), predList, listList);

        for (AbstractUnknownCard q : SneckoMod.unknownClasses) {
            addToLists(q, predList, listList);
        }
        addToLists(new UnknownColorless(), predList, listList);
        addToLists(new UnknownBoss(), predList, listList);

        AbstractUnknownCard.updateReplacements(predList, listList);
        resetOffclassList();
    }

    public static void resetOffclassList() {
        offclassCommons.clear();
        offclassUncommons.clear();
        offclassRares.clear();

        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.type != AbstractCard.CardType.STATUS && c.color != AbstractCard.CardColor.CURSE && c.type != AbstractCard.CardType.CURSE && c.rarity != AbstractCard.CardRarity.SPECIAL &&
                    !c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) && !c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) && !c.hasTag(AbstractCard.CardTags.HEALING) && !c.hasTag(SneckoMod.BANNEDFORSNECKO)) {
                if (SneckoMod.pureSneckoMode || SneckoMod.validColors.contains(c.color) || !(AbstractDungeon.player instanceof TheSnecko)) {
                    if (!(AbstractDungeon.player != null && AbstractDungeon.player.getCardColor() == c.color)) {
                        addOffclassToList(c);
                    }
                }
            }
        }
    }

    public static void addOffclassToList(AbstractCard c) {
        switch (c.rarity) {
            case COMMON:
                offclassCommons.add(c.cardID);
                break;
            case UNCOMMON:
                offclassUncommons.add(c.cardID);
                break;
            case RARE:
                offclassRares.add(c.cardID);
                break;
        }
    }

    public static void addStatusToList(AbstractCard c) {
        statuses.add(c);
    }

    public static AbstractCard getARandomStatus() {
        return statuses.get(AbstractDungeon.cardRandomRng.random(statuses.size() - 1)).makeCopy();
    }

    public static AbstractCard getARandomOffclass() {
        int roll = AbstractDungeon.cardRandomRng.random(99);
        ArrayList<String> rarityPool;
        if (roll < 55) {
            rarityPool = offclassCommons;
        } else if (roll < 85) {
            rarityPool = offclassUncommons;
        } else {
            rarityPool = offclassRares;
        }
        if (rarityPool.size() > 0) {
            return CardLibrary.cards.get(rarityPool.get(AbstractDungeon.cardRandomRng.random(rarityPool.size() - 1))).makeCopy();
        } else {
            return new Madness();
        }
    }

    public static ArrayList<AbstractCard> getXRandomOffclassCards(int amount) {
        ArrayList<AbstractCard> output = new ArrayList<>();
        ArrayList<String> oc = new ArrayList<>(offclassCommons),
                ou = new ArrayList<>(offclassUncommons),
                or = new ArrayList<>(offclassRares);

        for (int i = 0; i < amount; i++) {
            int roll = AbstractDungeon.cardRandomRng.random(99);
            if (roll < 55) {
                if (oc.isEmpty())
                    oc.addAll(offclassUncommons);

                int r = AbstractDungeon.cardRandomRng.random(oc.size() - 1);
                output.add(CardLibrary.cards.get(oc.get(r)).makeCopy());
                oc.remove(r);
            } else if (roll < 85) {
                if (ou.isEmpty())
                    ou.addAll(offclassUncommons);

                int r = AbstractDungeon.cardRandomRng.random(ou.size() - 1);
                output.add(CardLibrary.cards.get(ou.get(r)).makeCopy());
                ou.remove(r);
            } else {
                if (or.isEmpty())
                    or.addAll(offclassRares);

                int r = AbstractDungeon.cardRandomRng.random(or.size() - 1);
                output.add(CardLibrary.cards.get(or.get(r)).makeCopy());
                or.remove(r);
            }
        }
        return output;
    }
}
