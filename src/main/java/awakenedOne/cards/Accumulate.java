package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import downfall.util.CardIgnore;

import static awakenedOne.util.Wiz.applyToSelf;
@Deprecated
@CardIgnore
public class Accumulate extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Accumulate.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public Accumulate() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 4;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;

        this.baseSecondMagic = 2;
        this.secondMagic = this.baseSecondMagic;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        this.addToBot(new VFXAction(p, new InflameEffect(p), 1.0F));
       // applyToSelf(new StrengthOverTurnsPower(this.secondMagic, this.magicNumber));
    }


    @Override
    public void upp() {
        upgradeBlock(3);
    }
}