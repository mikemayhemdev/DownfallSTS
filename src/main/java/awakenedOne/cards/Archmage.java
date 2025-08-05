package awakenedOne.cards;

import awakenedOne.powers.DarkIncantationRitualPower;
import awakenedOne.powers.DoubleSpellPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class Archmage extends AbstractAwakenedCard {
    public final static String ID = makeID(Archmage.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Archmage() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(Archmage.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DarkIncantationRitualPower(1));
    }

    public void upp() {
        upgradeBaseCost(2);
    }

}