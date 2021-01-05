package champ.cards;

import champ.powers.ShieldWallPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class ShieldWall extends AbstractChampCard {

    public final static String ID = makeID("ShieldWall");

    //stupid intellij stuff power, self, uncommon

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 3;

    public ShieldWall() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ShieldWallPower(magicNumber));
        applyToSelf(new DexterityPower(p, 2));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}