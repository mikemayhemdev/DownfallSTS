package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.BurnPower;

public class PhantomFireball extends AbstractHexaCard {

    public final static String ID = makeID("PhantomFireball");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;

    public PhantomFireball() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 2; i++) {
            dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        }
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                if (m.hasPower(BurnPower.POWER_ID)) {
                    dmg(m, makeInfo(), AttackEffect.FIRE);
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}