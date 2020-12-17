package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Return extends AbstractBronzeCard {

    public final static String ID = makeID("Return");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    public Return() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new );
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}