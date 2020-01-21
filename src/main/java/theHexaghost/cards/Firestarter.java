package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.BurnPower;

public class Firestarter extends AbstractHexaCard {

    public final static String ID = makeID("Firestarter");

    //stupid intellij stuff ATTACK, ENEMY, COMMON

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;

    public Firestarter() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        applyToEnemy(m, new BurnPower(m, magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}