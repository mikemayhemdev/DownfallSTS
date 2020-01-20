package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;

public class SwordOfDarkness extends AbstractHexaCard {

    public final static String ID = makeID("SwordOfDarkness");

    //stupid intellij stuff ATTACK, ENEMY, COMMON

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 6;

    public SwordOfDarkness() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!GhostflameHelper.activeGhostFlame.charged) {
            atb(new GainEnergyAction(1));
        }
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}