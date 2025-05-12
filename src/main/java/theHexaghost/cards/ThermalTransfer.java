package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import theHexaghost.powers.BurnPower;

public class ThermalTransfer extends AbstractHexaCard {

    public final static String ID = makeID("ThermalTransfer");

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 2;

    public ThermalTransfer() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        HexaMod.loadJokeCardImage(this, "ThermalTransfer.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new FireballEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.4F));
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        if (m.hasPower(BurnPower.POWER_ID)) {
            atb(new GainBlockAction(p, block));
            atb(new VFXAction(new FireballEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY), 0.4F));
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