package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WildStrike extends AbstractBronzeCard {

    public final static String ID = makeID("WildStrike");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 5;

    public WildStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        shuffleIn(new Wound());
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}