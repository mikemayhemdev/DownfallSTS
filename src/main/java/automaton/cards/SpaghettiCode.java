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
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
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

    public static ArrayList<AbstractCard> getRandomEncodeChoices(int amount) {
        ArrayList<AbstractCard> eligibleCardsList = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.hasTag(AutomatonMod.ENCODES) && !c.hasTag(CardTags.HEALING) && c.rarity != CardRarity.SPECIAL) {
                eligibleCardsList.add(c.makeCopy());
            }
        }
        ArrayList<AbstractCard> selectedCards = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            selectedCards.add(eligibleCardsList.remove(AbstractDungeon.cardRandomRng.random(0, eligibleCardsList.size() - 1)));
        }
        return selectedCards;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < (FunctionHelper.max - FunctionHelper.held.size()); i++) {
            ArrayList<AbstractCard> cardsList = getRandomEncodeChoices(2);
            addToBot(new SelectCardsCenteredAction(cardsList, 1, "Choose a Card to Encode.", (cards) -> { //TODO: Needs localization
                addToTop(new AddToFuncAction(cards.get(0), null));
            }));
        }
    }

    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}