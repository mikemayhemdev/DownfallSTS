package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import downfall.util.CardIgnore;

import static awakenedOne.util.Wiz.applyToSelf;
@Deprecated
@CardIgnore
public class Fortitude extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Fortitude.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    boolean chant = false;

    public Fortitude() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 12;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        for (AbstractPower q : p.powers) {
            if (q.type == AbstractPower.PowerType.DEBUFF) {
                applyToSelf(new PlatedArmorPower(p, this.magicNumber));
            }
        }
    }


    @Override
    public void upp() {
        upgradeBlock(4);
        upgradeMagicNumber(1);
    }
}