package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.ChargeCurrentFlameAction;
import theHexaghost.actions.ExtinguishCurrentFlameAction;

public class HauntingEcho extends AbstractHexaCard {

    public final static String ID = makeID("HauntingEcho");

    //stupid intellij stuff ATTACK, SELF_AND_ENEMY, UNCOMMON

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;

    public HauntingEcho() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.SELF_AND_ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (GhostflameHelper.activeGhostFlame.charged) {
                    addToTop(new ChargeCurrentFlameAction());
                    addToTop(new ExtinguishCurrentFlameAction());
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