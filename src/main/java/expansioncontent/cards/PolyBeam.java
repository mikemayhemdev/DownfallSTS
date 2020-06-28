package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.PolyBeamAgainPower;
import expansioncontent.powers.PolyBeamAgainPowerUpgraded;


public class PolyBeam extends AbstractExpansionCard {
    public final static String ID = makeID("PolyBeam");

    private static final int DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 2;
    public boolean isACopy = false;

    public PolyBeam() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        tags.add(expansionContentMod.STUDY_SHAPES);
        tags.add(expansionContentMod.STUDY);

        baseDamage = DAMAGE;
        this.isMultiDamage = true;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));

        atb(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY), 0.3F));
        atb(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        atb(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));

        if (!this.isACopy) atb(new ApplyPowerAction(p, p, new PolyBeamAgainPower(p, p, this.magicNumber, this), this.magicNumber));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }

}

