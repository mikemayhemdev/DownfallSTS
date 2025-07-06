package awakenedOne.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawPower;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;
import com.megacrit.cardcrawl.vfx.combat.FastingEffect;

import static awakenedOne.AwakenedOneMod.*;

public class FadeOut extends AbstractAwakenedCard {
    public final static String ID = makeID(FadeOut.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public FadeOut() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        //baseSecondMagic = secondMagic = 3;
        this.isEthereal = true;
        loadJokeCardImage(this, makeBetaCardPath(FadeOut.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //applyToSelfTop(new HexMasterPower(magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new DrawPower(p, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new EnergyDownPower(p, 1, true), 1));
        if (p != null) {
            this.addToBot(new VFXAction(new FastingEffect(p.hb.cX, p.hb.cY, Color.BLUE)));
        }

        //this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.secondMagic), this.secondMagic));
        //this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.secondMagic), this.secondMagic));

        //this.addToBot(new ApplyPowerAction(p, p, new NihilPower(p, this.magicNumber), this.magicNumber));
        //this.addToBot(new ApplyPowerAction(p, p, new ManaburnPower(p, this.magicNumber), this.magicNumber));
    }

    public void upp() {
        //this.isEthereal = false;
        upgradeBaseCost(0);
        //upgradeMagicNumber(2);
        //upgradeSecondMagic(1);
    }

}