package awakenedOne.cards;

import awakenedOne.powers.ReverseRitualPower;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.applyToSelfTop;

public class MazalethForm extends AbstractAwakenedCard {
    public final static String ID = makeID(MazalethForm.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public MazalethForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(BaseModCardTags.FORM);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, secondMagic));
        applyToSelfTop(new CuriosityPower(p, secondMagic));
        applyToSelf(new ReverseRitualPower(-magicNumber));
    }

    @Override
    public void upp() {
        //isEthereal = false;
       // upgradeSecondMagic(1);
        upgradeMagicNumber(-1);
    }
}