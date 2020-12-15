package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OpenFire extends AbstractBronzeCard {

    public final static String ID = makeID("OpenFire");

    //stupid intellij stuff skill, self, basic

    public OpenFire() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        fireBlaster(1);
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}