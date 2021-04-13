package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.AddSameInstancePower;
import expansioncontent.powers.NextTurnExhumePower;

public class DecaShield extends AbstractExpansionCard {
    public final static String ID = makeID("DecaShield");

    private static final int BLOCK = 7;
    private static final int UPGRADE_BLOCK = 2;
    private static final int BLOCK_INCREASE = 1;

    public PolyBeam partner = null;

    public DecaShield(PolyBeam partner, boolean hasPreview) {
        super(ID, 2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_donudeca.png", "expansioncontentResources/images/1024/bg_boss_donudeca.png");
        tags.add(expansionContentMod.STUDY);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = BLOCK_INCREASE;
        this.exhaust = true;
        if (hasPreview) setPartner(partner);
    }

    public DecaShield() {
        this(null);
    }

    public DecaShield(PolyBeam partner) {
        this(partner, true);
    }

    public void setPartner(PolyBeam partner) {
        this.partner = partner;
        if (partner != null) {
            cardsToPreview = this.partner;
        } else {
            cardsToPreview = new PolyBeam(null, false);
            if (this.upgraded) cardsToPreview.upgrade();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        blck();

        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                baseBlock += magicNumber;
            }
        });

        if (this.partner == null) {
            PolyBeam partner = new PolyBeam(this, true);
            if (this.upgraded) partner.upgrade();
            setPartner(partner);
            applyToSelf(new AddSameInstancePower(1, this.partner,
                    expansionContentMod.getModID() + "Resources/images/powers/Donu84.png",
                    expansionContentMod.getModID() + "Resources/images/powers/Donu32.png"));
        } else {
            this.partner.setPartner(this);
            applyToSelf(new NextTurnExhumePower(1, this.partner,
                    expansionContentMod.getModID() + "Resources/images/powers/Donu84.png",
                    expansionContentMod.getModID() + "Resources/images/powers/Donu32.png"));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            if (cardsToPreview != null) cardsToPreview.upgrade();
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        DecaShield copy = (DecaShield) super.makeStatEquivalentCopy();
        copy.setPartner(partner);
        return copy;
    }

    @Override
    public AbstractCard makeCopy() {
        return new DecaShield(partner);
    }
}