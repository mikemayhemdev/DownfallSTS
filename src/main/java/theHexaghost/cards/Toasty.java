package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.actions.BurningHitAction;
import theHexaghost.actions.ChargeCurrentFlameAction;
import theHexaghost.actions.ExtinguishCurrentFlameAction;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Toasty extends AbstractHexaCard {

    public final static String ID = makeID("Toasty");

    //stupid intellij stuff ATTACK, ENEMY, RARE

    private static final int DAMAGE = 14;
    private static final int UPG_DAMAGE = 4;

    public Toasty() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        HexaMod.loadJokeCardImage(this, "Toasty.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        atb(new BurningHitAction(m, p, damage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        atb(new ExtinguishCurrentFlameAction());
        atb(new ChargeCurrentFlameAction());
    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}