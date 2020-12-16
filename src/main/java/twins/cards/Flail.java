package twins.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

public class Flail extends AbstractTwinsCard {

    public final static String ID = makeID("Flail");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    public Flail() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        applyToSelf(new DrawCardNextTurnPower(p, 1));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}