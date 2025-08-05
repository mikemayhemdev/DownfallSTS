package awakenedOne.cards;

import awakenedOne.powers.SpellshieldPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;

import static awakenedOne.AwakenedOneMod.*;

public class Spellshield extends AbstractAwakenedCard {
    public final static String ID = makeID(Spellshield.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public Spellshield() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath(Spellshield.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new SpellshieldPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}