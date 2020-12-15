package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TriBeam extends AbstractBronzeCard {

    public final static String ID = makeID("TriBeam");

    //stupid intellij stuff attack, all_enemy, uncommon

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 4;

    public TriBeam() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        fireBlaster(2);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}