package expansioncontent.cards;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import expansioncontent.powers.StudyTheSpirePower;


public class StudyTheSpire extends AbstractExpansionCard {
    public final static String ID = makeID("StudyTheSpire");

    public StudyTheSpire() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_power.png", "expansioncontentResources/images/1024/bg_boss_power.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(p, new BorderFlashEffect(Color.GREEN, true), 0.05F, true));
        atb(new VFXAction(p, new IntenseZoomEffect(p.hb.cX, p.hb.cY, false), 0.05F));
        applyToSelf(new StudyTheSpirePower(p, magicNumber, upgraded));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}