package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReserveGuard extends AbstractBronzeCard {

    public final static String ID = makeID("ReserveGuard");

    //stupid intellij stuff skill, self, special

    public ReserveGuard() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        selfRetain = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        fireShields(1);
    }

    public void upp() {
    }
}