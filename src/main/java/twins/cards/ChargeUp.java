package twins.cards;

import twins.DonuDecaMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ChargeUp extends AbstractTwinsCard {

    public final static String ID = makeID("ChargeUp");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 3;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public ChargeUp() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(DonuDecaMod.SHIELD);
        tags.add(DonuDecaMod.BURNOUT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new StrengthPower(p, magicNumber));
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
        upgradeMagicNumber(UPG_MAGIC);
    }
}