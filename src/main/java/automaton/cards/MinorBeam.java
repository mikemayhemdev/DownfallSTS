package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MinorBeam extends AbstractBronzeCard {

    public final static String ID = makeID("MinorBeam");

    //stupid intellij stuff attack, enemy, special

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    public MinorBeam() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = DAMAGE;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}