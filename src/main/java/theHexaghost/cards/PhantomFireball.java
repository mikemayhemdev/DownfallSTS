package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.powers.BurnPower;
import theHexaghost.vfx.ExplosionSmallEffectGreen;

public class PhantomFireball extends AbstractHexaCard {

    public final static String ID = makeID("PhantomFireball");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;

    public PhantomFireball() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                if (m.hasPower(BurnPower.POWER_ID)) {
                    BurnPower p = (BurnPower) m.getPower(BurnPower.POWER_ID);
                    p.explode();
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void triggerOnGlowCheck() {
        burnGlowCheck();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}