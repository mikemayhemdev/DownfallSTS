package awakenedOne.cards.tokens.spells;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class GreaterGigantify extends AbstractSpellCard {
    public final static String ID = makeID(GreaterGigantify.class.getSimpleName());
    // intellij stuff power, self, , , , , 3, 12

    public GreaterGigantify() {
        super(ID, CardType.POWER, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, magicNumber));
        applyToSelf(new DexterityPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}