package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ChargeUp extends AbstractBronzeCard {

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
        tags.add(AutomatonMod.SHIELD);
        tags.add(AutomatonMod.BURNOUT);
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