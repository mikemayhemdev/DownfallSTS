package awakenedOne.cards;

import awakenedOne.powers.DominusPower;
import awakenedOne.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FastingEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class DemonGlyph extends AbstractAwakenedCard {
    public final static String ID = makeID(DemonGlyph.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public DemonGlyph() {
        super(ID, 0, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 2;
        this.isEthereal = true;
        loadJokeCardImage(this, makeBetaCardPath(DemonGlyph.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));

        if (p != null) {
            this.addToBot(new VFXAction(new FastingEffect(p.hb.cX, p.hb.cY, Color.BLUE)));
        }

        if (Wiz.isAwakened()) {
            this.addToBot(new VFXAction(p, new InflameEffect(p), 1.0F));
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, secondMagic), secondMagic));
            this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, secondMagic), secondMagic));
        } else {
            applyToSelf(new DominusPower(secondMagic));
        }
    }


    public void triggerOnGlowCheck() {
        this.glowColor = (Wiz.isAwakened()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        //upgradeMagicNumber(1);
        upgradeSecondMagic(1);
    }

}