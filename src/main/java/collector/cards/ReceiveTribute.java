package collector.cards;

import collector.cards.AbstractCollectorCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class ReceiveTribute extends AbstractCollectorCard {
    public final static String ID = makeID(ReceiveTribute.class.getSimpleName());
    // intellij stuff skill, self, rare, , , , , 2, 2

    public ReceiveTribute() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}