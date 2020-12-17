package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class ReverseEngineer extends AbstractBronzeCard {

    public final static String ID = makeID("ReverseEngineer");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 3;

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public ReverseEngineer() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.getIntentBaseDmg() > -1) {
            applyToSelf(new DexterityPower(p, magicNumber));
        }
        else {
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
    }
}