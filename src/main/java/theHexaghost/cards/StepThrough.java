package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.actions.ChargeCurrentFlameAction;
import theHexaghost.ghostflames.SearingGhostflame;

public class StepThrough extends AbstractHexaCard {

    public final static String ID = makeID("StepThrough");

    //stupid intellij stuff ATTACK, SELF_AND_ENEMY, UNCOMMON

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public StepThrough() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.POISON);
        if (GhostflameHelper.activeGhostFlame.charged) {
            atb(new AdvanceAction(false));
        } else {
            if (GhostflameHelper.activeGhostFlame instanceof SearingGhostflame){
                SearingGhostflame gf = (SearingGhostflame) GhostflameHelper.activeGhostFlame;
                if (gf.attacksPlayedThisTurn == 1){
                    atb(new AdvanceAction(false));
                } else {
                    atb(new ChargeCurrentFlameAction());
                }
            } else {
                atb(new ChargeCurrentFlameAction());
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}