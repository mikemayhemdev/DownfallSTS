package twins.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Twinshot extends AbstractTwinsCard {

    public final static String ID = makeID("Twinshot");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 6;

    public Twinshot() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        fireBlaster(1);
    }

    public void upp() {
        upgradeDamage(3);
    }
}