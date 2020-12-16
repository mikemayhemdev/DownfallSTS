package twins.cards;

import twins.TwinsHelper;
import twins.actions.RepeatCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FullForce extends AbstractTwinsCard {

    public final static String ID = makeID("FullForce");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 4;

    public FullForce() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        for (AbstractCard c : TwinsHelper.blasters.group) {
            atb(new RepeatCardAction(c));
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}