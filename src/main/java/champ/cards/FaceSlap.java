package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FaceSlap extends AbstractChampCard {

    public final static String ID = makeID("FaceSlap");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public FaceSlap() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        gladOpen();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (bcombo()) applyToEnemy(m, autoVuln(m, magicNumber));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
    }
}