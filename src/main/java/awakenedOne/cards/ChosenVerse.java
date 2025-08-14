package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.ChosensVersePower;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;


public class ChosenVerse extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(ChosenVerse.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public ChosenVerse() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 3;
        baseMagicNumber = magicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath(ChosenVerse.class.getSimpleName() + ".png"));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ChosensVersePower(magicNumber, block));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        upgradeBlock(2);
    }
}