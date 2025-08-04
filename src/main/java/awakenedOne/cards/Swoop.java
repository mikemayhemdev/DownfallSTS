package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.ChosensVersePower;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReboundPower;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;


public class Swoop extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Swoop.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Swoop() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 4;
        baseMagicNumber = magicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath(Swoop.class.getSimpleName() + ".png"));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ChosensVersePower(magicNumber, block));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        upgradeBlock(1);
    }
}