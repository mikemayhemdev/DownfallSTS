package timeEater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend extends AbstractTimeCard {

    public final static String ID = makeID("Defend");

    // intellij stuff skill, self, basic

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;

    public Defend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}