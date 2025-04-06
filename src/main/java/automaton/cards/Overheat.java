package automaton.cards;

import automaton.AutomatonMod;
import automaton.powers.RemoveNextErrorPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Overheat extends AbstractBronzeCard {

    public final static String ID = makeID("Overheat");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 18;
    private static final int UPG_DAMAGE = 4;

    public Overheat() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("Overheat.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        applyToSelf(new RemoveNextErrorPower(1));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}