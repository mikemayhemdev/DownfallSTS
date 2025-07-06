package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.NextPowerAOEPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.util.Wiz.applyToSelf;

public class Psalm extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(Psalm.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Psalm() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 5;
        magicNumber = baseMagicNumber = 5;
        loadJokeCardImage(this, makeBetaCardPath(Psalm.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new NextPowerAOEPower(this.magicNumber));
    }

    @Override
    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(3);
    }
}