package expansioncontent.cards;


import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cardmods.EtherealMod;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.AddCopyPower;


public class DecaShield extends AbstractExpansionCard {
    public final static String ID = makeID("DecaShield");

    public boolean isAPreview;

    public DecaShield(boolean noHover) {
        super(ID, 2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_donudeca.png", "expansioncontentResources/images/1024/bg_boss_donudeca.png");
        baseBlock = 14;
        isEthereal = true;
        this.exhaust = true;
        if (!noHover) {
            PolyBeam q = new PolyBeam(true);
            CardModifierManager.addModifier(q, new EtherealMod());
            q.isAPreview = true;
            if (upgraded) q.upgrade();
            cardsToPreview = q;
        }
        tags.add(expansionContentMod.STUDY);
    }

    public DecaShield() {
        this(false);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        AbstractCard q = new PolyBeam();
        if (upgraded) q.upgrade();
        CardModifierManager.addModifier(q, new EtherealMod());
        applyToSelf(new AddCopyPower(1, q,
                expansionContentMod.getModID() + "Resources/images/powers/Donu84.png",
                expansionContentMod.getModID() + "Resources/images/powers/Donu32.png"));
    }

    public void upgrade() {
        if (!upgraded) {
            if (!isAPreview) cardsToPreview.upgrade();
            upgradeName();
            upgradeBlock(4);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}

