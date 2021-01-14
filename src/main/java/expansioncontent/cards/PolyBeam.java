package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.AddCopyPower;


public class PolyBeam extends AbstractExpansionCard {
    public final static String ID = makeID("PolyBeam");

    private static final int DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;

    public boolean isAPreview;

    public PolyBeam(boolean noHover) {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_donudeca.png", "expansioncontentResources/images/1024/bg_boss_donudeca.png");
        tags.add(expansionContentMod.STUDY_SHAPES);
        tags.add(expansionContentMod.STUDY);
        baseDamage = DAMAGE;
        this.exhaust = true;
        if (!noHover) {
            DecaShield q = new DecaShield(true);
            q.isAPreview = true;
            if (upgraded) q.upgrade();
            cardsToPreview = q;
        }
    }

    public PolyBeam() {
        this(false);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
        atb(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY), 0.3F));
        atb(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        atb(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        applyToSelf(new AddCopyPower(1,
                cardsToPreview.makeStatEquivalentCopy(),
                expansionContentMod.getModID() + "Resources/images/powers/StudyShapes84.png",
                expansionContentMod.getModID() + "Resources/images/powers/StudyShapes32.png"));

    }

    public void upgrade() {
        if (!upgraded) {
            if (!isAPreview) cardsToPreview.upgrade();
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}

