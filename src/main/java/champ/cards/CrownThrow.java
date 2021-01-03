package champ.cards;

import champ.powers.BoomerangPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CrownThrow extends AbstractChampCard {

    public final static String ID = makeID("CrownThrow");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    public CrownThrow() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        // TODO: Do a throw vfx. First you throw a crown...
        if (this.costForTurn != 0 && !freeToPlayOnce) {
            applyToSelf(new BoomerangPower(this));
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}