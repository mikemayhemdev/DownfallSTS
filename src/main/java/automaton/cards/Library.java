package automaton.cards;

import automaton.powers.LibraryPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BerserkPower;

public class Library extends AbstractBronzeCard {

    public final static String ID = makeID("Library");

    //stupid intellij stuff power, self, rare

    public Library() {
        super(ID, 0, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        magicNumber = baseMagicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BerserkPower(p,1));

        atb(new MakeTempCardInDrawPileAction(new VoidCard(), magicNumber, true, true));
    }

    public void upp() {
        upgradeMagicNumber(-2);
    }
}