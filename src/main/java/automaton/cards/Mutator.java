package automaton.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Mutator extends AbstractBronzeCard {

    public final static String ID = makeID("Mutator");

    //stupid intellij stuff skill, self, uncommon

    public Mutator() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, 1));
        AbstractCard q = this;
        atb(new SelectCardsInHandAction(1, masterUI.TEXT[6], c -> c.type == CardType.STATUS, (cards) -> {
            att(new MakeTempCardInHandAction(q.makeStatEquivalentCopy(), true));
            att(new ExhaustSpecificCardAction(cards.get(0), p.hand, true));
        }));
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}