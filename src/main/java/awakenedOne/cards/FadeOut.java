package awakenedOne.cards;

import awakenedOne.powers.NihilPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FastingEffect;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeID;

public class FadeOut extends AbstractAwakenedCard {
    public final static String ID = makeID(FadeOut.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public FadeOut() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        baseSecondMagic = secondMagic = 3;
        this.isEthereal = true;
        loadJokeCardImage(this, ID+".png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //applyToSelfTop(new HexMasterPower(magicNumber));
        if (p != null) {
            this.addToBot(new VFXAction(new FastingEffect(p.hb.cX, p.hb.cY, Color.BLUE)));
        }

        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.secondMagic), this.secondMagic));
        this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.secondMagic), this.secondMagic));

        this.addToBot(new ApplyPowerAction(p, p, new NihilPower(p, this.magicNumber), this.magicNumber));
        //this.addToBot(new ApplyPowerAction(p, p, new ManaburnPower(p, this.magicNumber), this.magicNumber));
    }

    public void upp() {
        //this.isEthereal = false;
        upgradeMagicNumber(2);
        upgradeSecondMagic(1);
    }

}