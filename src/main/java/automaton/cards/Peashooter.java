package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Peashooter extends AbstractBronzeCard {

    public final static String ID = makeID("Peashooter");

    //stupid intellij stuff attack, enemy, basic

    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 1;

    public Peashooter() {
        super(ID, 0, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(AutomatonMod.BLASTER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}