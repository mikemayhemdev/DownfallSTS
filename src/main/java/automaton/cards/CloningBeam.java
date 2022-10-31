package automaton.cards;

import automaton.AutomatonMod;
import automaton.powers.CloningPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;

public class CloningBeam extends AbstractBronzeCard {

    public final static String ID = makeID("CloningBeam");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 14;

    public CloningBeam() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        exhaust = true;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("CloningBeam.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        applyToSelf(new CloningPower(1));
    }

    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}