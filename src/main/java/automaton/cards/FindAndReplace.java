package automaton.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class FindAndReplace extends AbstractBronzeCard {

    public final static String ID = makeID("FindAndReplace");

    //stupid intellij stuff skill, self, rare

    private static final int MAGIC = 1;

    public FindAndReplace() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        cardsList.addAll(p.drawPile.group);
        cardsList.addAll(p.discardPile.group);
        atb(new SelectCardsAction(cardsList, 1, "Choose.", (cards) -> {
            if (p.drawPile.contains(cards.get(0))) {
                p.drawPile.moveToHand(cards.get(0));
                addToTop(new MakeTempCardInDrawPileAction(new Dazed(), 1, true, true));
            } else if (p.discardPile.contains(cards.get(0))) {
                p.discardPile.moveToHand(cards.get(0));
                addToTop(new MakeTempCardInDiscardAction(new Dazed(), 1));
            }
        }));
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}