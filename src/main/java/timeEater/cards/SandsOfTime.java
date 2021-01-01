package timeEater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SandsOfTime extends AbstractTimeCard {

    public final static String ID = makeID("SandsOfTime");

    // intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 20;
    private static final int UPG_DAMAGE = 6;

    public SandsOfTime() {
        super(ID, 4, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        selfRetain = true;
    }

    public void onRetained() {
        this.addToBot(new ReduceCostAction(this));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}