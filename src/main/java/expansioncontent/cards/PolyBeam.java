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
import expansioncontent.powers.AddSameInstancePower;
import expansioncontent.powers.NextTurnExhumePower;

public class PolyBeam extends AbstractExpansionCard {
    public final static String ID = makeID("PolyBeam");

    private static final int DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int DAMAGE_INCREASE = 2;

    public DecaShield partner = null;

    public PolyBeam(DecaShield partner, boolean hasPreview) {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_donudeca.png", "expansioncontentResources/images/1024/bg_boss_donudeca.png");
        tags.add(expansionContentMod.STUDY_SHAPES);
        tags.add(expansionContentMod.STUDY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = DAMAGE_INCREASE;
        this.exhaust = true;
        //partner is null until this card is played in combat
        if (hasPreview) setPartner(partner);
    }

    public PolyBeam(DecaShield partner) {
        this(partner, true);
    }

    public PolyBeam() {
        this(null);
    }

    public void setPartner(DecaShield partner) {
        this.partner = partner;
        if (partner != null) {
            cardsToPreview = partner.makeStatEquivalentCopy(true);
        } else {
            cardsToPreview = new DecaShield(null, false);
            if (this.upgraded) cardsToPreview.upgrade();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
        atb(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY), 0.3F));
        atb(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));

        atb(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
        atb(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY), 0.3F));
        atb(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));

        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                baseDamage += magicNumber;
            }
        });

        if (this.partner == null) {
            DecaShield partner = new DecaShield(this, true);
            if (this.upgraded) partner.upgrade();
            setPartner(partner);
            applyToSelf(new AddSameInstancePower(1, this.partner,
                    expansionContentMod.getModID() + "Resources/images/powers/StudyShapes84.png",
                    expansionContentMod.getModID() + "Resources/images/powers/StudyShapes32.png"));
        } else {
            this.partner.setPartner(this);
            applyToSelf(new NextTurnExhumePower(1, this.partner,
                    expansionContentMod.getModID() + "Resources/images/powers/StudyShapes84.png",
                    expansionContentMod.getModID() + "Resources/images/powers/StudyShapes32.png"));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            if (cardsToPreview != null && !cardsToPreview.upgraded) cardsToPreview.upgrade();
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        PolyBeam copy = (PolyBeam) super.makeStatEquivalentCopy();
        copy.setPartner(partner);
        return copy;
    }

    public AbstractCard makeStatEquivalentCopy(boolean isPreview) {
        PolyBeam copy = (PolyBeam) super.makeStatEquivalentCopy();
        if (!isPreview) copy.setPartner(partner);
        return copy;
    }

    @Override
    public AbstractCard makeCopy() {
        return new PolyBeam(partner, false);
    }
}