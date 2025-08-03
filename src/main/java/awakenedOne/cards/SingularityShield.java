package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import expansioncontent.powers.DeEnergizedPower;
import hermit.powers.Drained;

import static awakenedOne.AwakenedOneMod.*;

public class SingularityShield extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(SingularityShield.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public SingularityShield() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseBlock = 7;
        loadJokeCardImage(this, makeBetaCardPath(SingularityShield.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.applyToSelf(new Drained(p, p, 1));
        Wiz.applyToSelf(new NextTurnBlockPower(p, block));
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }
}