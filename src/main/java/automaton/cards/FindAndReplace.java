package automaton.cards;

import automaton.AutomatonMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Comparator;

import static automaton.AutomatonMod.makeBetaCardPath;

public class FindAndReplace extends AbstractBronzeCard {

    public final static String ID = makeID("FindAndReplace");

    private static final int MAGIC = 1;

    public FindAndReplace() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("FindAndReplace.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        cardsList.addAll(p.drawPile.group);
        cardsList.addAll(p.discardPile.group);
        cardsList.sort(Comparator.comparing(o -> o.name));

        atb(new SelectCardsAction(cardsList, 1, EXTENDED_DESCRIPTION[0], (cards) -> {
            AbstractCard selectedCard = cards.get(0);

            if (p.drawPile.contains(selectedCard)) {
                int index = p.drawPile.group.indexOf(selectedCard);
                p.drawPile.moveToHand(selectedCard);
                if (!upgraded) {
                    addToTop(new MakeTempCardInDrawPileAction(new Dazed(), 1, true, true));
                } else {
                    p.drawPile.group.add(index, this);
                    p.drawPile.removeCard(this);
                }
            } else if (p.discardPile.contains(selectedCard)) {
                int index = p.discardPile.group.indexOf(selectedCard);
                p.discardPile.moveToHand(selectedCard);
                if (!upgraded) {
                    addToTop(new MakeTempCardInDiscardAction(new Dazed(), 1));
                } else {
                    p.discardPile.group.add(index, this);
                    p.discardPile.removeCard(this);
                }
            }
        }));
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}
