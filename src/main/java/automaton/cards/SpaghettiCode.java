package automaton.cards;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.actions.AddToFuncAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;

import java.util.ArrayList;

public class SpaghettiCode extends AbstractBronzeCard {

    public final static String ID = makeID("SpaghettiCode");

    //stupid intellij stuff skill, self, rare

    public SpaghettiCode() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

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
            if (c.hasTag(AutomatonMod.ENCODES) && !c.hasTag(CardTags.HEALING) && c.rarity != CardRarity.SPECIAL) {
                eligibleCardsList.add(c.makeCopy());
            }
        }
        AbstractCard qCardGet = eligibleCardsList.get(AbstractDungeon.cardRandomRng.random(0, eligibleCardsList.size() - 1));
        return qCardGet.makeCopy();
    }

    public static ArrayList<AbstractCard> getRandomEncodeChoices() {
        ArrayList<AbstractCard> eligibleCardsList = new ArrayList<>();

        eligibleCardsList.add(getRandomEncodeWithCost(0).makeCopy());
        eligibleCardsList.add(getRandomEncodeWithCost(1).makeCopy());
        eligibleCardsList.add(getRandomEncodeWithCost(2).makeCopy());
        return eligibleCardsList;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < (FunctionHelper.max() - FunctionHelper.held.size()); i++) {
            ArrayList<AbstractCard> cardsList = getRandomEncodeChoices();
            addToBot(new SelectCardsCenteredAction(cardsList, 1, masterUI.TEXT[7], (cards) -> {
                addToTop(new AddToFuncAction(cards.get(0), null));
            }));
        }
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}