package awakenedOne.cards;

import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.powers.ThaumaturgyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class Thaumaturgy extends AbstractAwakenedCard {
    public final static String ID = makeID(Thaumaturgy.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Thaumaturgy() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        baseSecondMagic = secondMagic = 1;
        cardsToPreview = new Ceremony();
        loadJokeCardImage(this, makeBetaCardPath(Thaumaturgy.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DexterityPower(p, secondMagic));
        applyToSelf(new ThaumaturgyPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}