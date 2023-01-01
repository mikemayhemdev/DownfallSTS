package awakenedOne.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.awakenedAmount;

public class BookOfTricks extends AbstractAwakenedCard {
    public final static String ID = makeID(BookOfTricks.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 4, 2

    public BookOfTricks() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        baseSecondMagic = secondMagic = 2;
        baseThirdMagic = thirdMagic = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int amt = awakenedAmount();
        applyToSelf(new PlatedArmorPower(p, magicNumber));
        if (amt >= 1) {
            applyToSelf(new ArtifactPower(p, secondMagic));
        }
        if (amt >= 3) {
            applyToSelf(new ThornsPower(p, thirdMagic));
        }
    }

    public void upp() {
        upgradeMagicNumber(2);
        upgradeSecondMagic(1);
        upgradeThirdMagic(3);
    }
}