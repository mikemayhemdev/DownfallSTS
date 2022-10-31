package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.util.CardIgnore;

@CardIgnore
public class PhantomCloak extends AbstractHexaCard {

    public final static String ID = makeID("PhantomCloak");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public PhantomCloak() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DexterityPower(p, magicNumber));
        applyToSelf(new StrengthPower(p, -1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}