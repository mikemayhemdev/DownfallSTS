package timeEater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeEater.ClockHelper;

public class MidnightStrike extends AbstractTimeCard {

    public final static String ID = makeID("MidnightStrike");

    // intellij stuff attack, enemy, rare

    private static final int DAMAGE = 50;

    public MidnightStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (ClockHelper.clock != 11) {
            cantUseMessage = EXTENDED_DESCRIPTION[0];
            return false;
        }
        return super.canUse(p, m);
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}