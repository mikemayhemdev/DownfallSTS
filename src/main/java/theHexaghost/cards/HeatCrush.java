package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import theHexaghost.powers.BurnPower;
import theHexaghost.vfx.HeatCrushEffect;

public class HeatCrush extends AbstractHexaCard {

    public final static String ID = makeID("HeatCrush");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON

    private static final int DAMAGE = 20;
    private static final int UPG_DAMAGE = 10;

    public HeatCrush() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;// 70
        if (mo.hasPower(BurnPower.POWER_ID))
            baseDamage += mo.getPower(BurnPower.POWER_ID).amount;
        super.calculateCardDamage(mo);// 73
        this.baseDamage = realBaseDamage;// 75
        this.isDamageModified = this.damage != this.baseDamage;// 78
    }// 79

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {// 36
            this.addToBot(new VFXAction(new HeatCrushEffect(m.hb.cX, m.hb.cY)));// 37
        }
        this.addToBot(new WaitAction(0.8F));// 39
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}