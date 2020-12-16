package automaton.cards;

import automaton.actions.RepeatCardAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Override extends AbstractBronzeCard {

    public final static String ID = makeID("Override");

    //stupid intellij stuff skill, self, rare

    public Override() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsAction(p.hand.group, 1, "Choose.", (cards)-> {
            AbstractCard card = cards.get(0);
            addToTop(new RepeatCardAction(card));
        }));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}