package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.ChargeAction;
import theHexaghost.ghostflames.AbstractGhostflame;

public class UnlimitedPower extends AbstractHexaCard {

    public final static String ID = makeID("UnlimitedPower");

    //stupid intellij stuff ATTACK, ALL, RARE

    private static final int DAMAGE = 10;

    public UnlimitedPower() {
        super(ID, 4, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL);
        baseDamage = DAMAGE;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
            if (!gf.charged) {
                atb(new ChargeAction(gf));
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(3);
        }
    }
}