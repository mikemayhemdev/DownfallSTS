package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE;

public class SearingBeam extends AbstractBronzeCard {

    public final static String ID = makeID("SearingBeam");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 8;

    public SearingBeam(int upgrades) {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        thisEncodes();
        this.timesUpgraded = upgrades;
    }

    public SearingBeam() {
        this(0);
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        upgradeDamage(2 + timesUpgraded);
        ++timesUpgraded;
        upgraded = true;
        name = NAME + "+" + timesUpgraded;
        initializeTitle();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, FIRE);
    }

    public void upp() {
    }
}