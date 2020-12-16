package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class DeferProcess extends AbstractBronzeCard {

    public final static String ID = makeID("DeferProcess");

    //stupid intellij stuff skill, self, common

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public DeferProcess() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, autoWeak(m, magicNumber));
        applyToSelf(new EnergizedPower(p, 1));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}