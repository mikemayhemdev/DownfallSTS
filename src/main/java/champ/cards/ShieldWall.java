package champ.cards;

import champ.powers.ShieldWallPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShieldWall extends AbstractChampCard {

    public final static String ID = makeID("ShieldWall");

    //stupid intellij stuff power, self, uncommon

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public ShieldWall() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ShieldWallPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}