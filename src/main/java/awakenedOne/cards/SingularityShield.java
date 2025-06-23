package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class SingularityShield extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(SingularityShield.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public SingularityShield() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseBlock = 6;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, makeBetaCardPath(ID + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        HexCurse(magicNumber, m, p);
    }

    @Override
    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(1);
    }
}