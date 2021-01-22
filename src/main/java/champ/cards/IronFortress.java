package champ.cards;

import champ.powers.IronFortressPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IronFortress extends AbstractChampCard {

    public final static String ID = makeID("IronFortress");

    //stupid intellij stuff power, self, uncommon

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public IronFortress() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new IronFortressPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}