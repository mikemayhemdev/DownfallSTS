package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HardenedForm extends AbstractBronzeCard {

    public final static String ID = makeID("HardenedForm");

    //stupid intellij stuff power, self, rare

    private static final int MAGIC = 8;
    private static final int UPG_MAGIC = 3;

    public HardenedForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}