package automaton.cards;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.actions.AddToFuncAction;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cardmods.ExhaustMod;
import downfall.util.SelectCardsCenteredAction;

import java.util.ArrayList;

public class SpaghettiCode extends AbstractBronzeCard {

    public final static String ID = makeID("SpaghettiCode");

    //stupid intellij stuff skill, self, rare

    public SpaghettiCode() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("SpaghettiCode.png"));

        CardModifierManager.addModifier(this, new ExhaustMod());

        tags.add(AutomatonMod.ENCODES);

    }
/*
    public static AbstractCard getRandomEncodeWithCost(int cost) {
        ArrayList<AbstractCard> eligibleCardsList = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.hasTag(AutomatonMod.ENCODES) && !c.hasTag(CardTags.HEALING) && c.rarity != CardRarity.SPECIAL) {
                if (c.cost == cost) {
                    eligibleCardsList.add(c.makeCopy());
                }
            }
        }
        AbstractCard qCardGet = eligibleCardsList.get(AbstractDungeon.cardRandomRng.random(0, eligibleCardsList.size() - 1));
        return qCardGet.makeCopy();
    }

    public static AbstractCard getRandomEncode() {
        ArrayList<AbstractCard> eligibleCardsList = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.hasTag(AutomatonMod.ENCODES) && !c.hasTag(CardTags.HEALING) && c.rarity != CardRarity.SPECIAL && c.rarity != CardRarity.BASIC) {
                eligibleCardsList.add(c.makeCopy());
            }
        }
        AbstractCard qCardGet = eligibleCardsList.get(AbstractDungeon.cardRandomRng.random(0, eligibleCardsList.size() - 1));
        return qCardGet.makeCopy();
    }

    public static AbstractCard getRandomEncode(ArrayList<AbstractCard> exceptions) {
        ArrayList<AbstractCard> eligibleCardsList = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.hasTag(AutomatonMod.ENCODES) && !c.hasTag(CardTags.HEALING) && c.rarity != CardRarity.SPECIAL && c.rarity != CardRarity.BASIC) {
                eligibleCardsList.add(c.makeCopy());
            }
        }
        eligibleCardsList.removeIf(q -> exceptions.stream().anyMatch(q2 -> q2.cardID.equals(q.cardID)));
        AbstractCard qCardGet = eligibleCardsList.get(AbstractDungeon.cardRandomRng.random(0, eligibleCardsList.size() - 1));
        return qCardGet.makeCopy();
    }

    public static ArrayList<AbstractCard> getRandomEncodeChoices() {
        ArrayList<AbstractCard> eligibleCardsList = new ArrayList<>();

        eligibleCardsList.add(getRandomEncode().makeCopy());
        eligibleCardsList.add(getRandomEncode(eligibleCardsList).makeCopy());
        eligibleCardsList.add(getRandomEncode(eligibleCardsList).makeCopy());
        return eligibleCardsList;
    }

 */

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard newCard;
        for (int i = 0; i < magicNumber; i++) {
            newCard = CardLibrary.getCard(AutomatonMod.spaghettiOptions.get(AbstractDungeon.cardRandomRng.random(0, AutomatonMod.spaghettiOptions.size() - 1)));

            addCardToFunction(newCard);
        }
    }


    public void upp() {
        CardModifierManager.removeModifiersById(this, ExhaustMod.ID, true);
    }
}