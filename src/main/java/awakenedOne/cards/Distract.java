package awakenedOne.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReboundPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToEnemy;
import static awakenedOne.util.Wiz.applyToSelf;

public class Distract extends AbstractAwakenedCard {
    public final static String ID = makeID(Distract.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , 3, 1, 1, 1

    public Distract() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseBlock = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new ReboundPower(p));
    }

    public void upp() {
        upgradeBlock(3);
    }
}