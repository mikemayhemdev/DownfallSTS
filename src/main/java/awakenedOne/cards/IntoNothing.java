package awakenedOne.cards;

import awakenedOne.powers.IntoNothingPower;
import awakenedOne.powers.ManaburnPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.HexCurse;
import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class IntoNothing extends AbstractAwakenedCard {
    public final static String ID = makeID(IntoNothing.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public IntoNothing() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        HexCurse(magicNumber, m, p);
        this.addToBot(new ApplyPowerAction(m, p, new ManaburnPower(m, this.magicNumber), this.magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}