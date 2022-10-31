package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.actions.RetractAction;

public class BacktrackSmack extends AbstractHexaCard {

    public final static String ID = makeID("BacktrackSmack");

    //stupid intellij stuff ATTACK, ENEMY, COMMON

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 3;

    public BacktrackSmack() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(HexaMod.GHOSTWHEELCARD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        atb(new RetractAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}