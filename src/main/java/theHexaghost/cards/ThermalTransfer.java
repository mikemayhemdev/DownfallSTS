package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import theHexaghost.powers.BurnPower;

public class ThermalTransfer extends AbstractHexaCard {

    public final static String ID = makeID("ThermalTransfer");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    public ThermalTransfer() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new FireballEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.5F));
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        if (m.hasPower(BurnPower.POWER_ID)) {
            addToTop(new GainBlockAction(p, block));
            addToTop(new VFXAction(new FireballEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY), 0.5F));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        burnGlowCheck();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeBlock(UPG_BLOCK);
        }
    }
}