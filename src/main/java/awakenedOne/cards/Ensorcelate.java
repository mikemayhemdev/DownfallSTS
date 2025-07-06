package awakenedOne.cards;

import awakenedOne.powers.ConjureNextTurnPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class Ensorcelate extends AbstractAwakenedCard {
    public final static String ID = makeID(Ensorcelate.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Ensorcelate() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 8;
        //baseMagicNumber = magicNumber = 2;
        //this.tags.add(AwakenedOneMod.DELVE);
        loadJokeCardImage(this, makeBetaCardPath(Ensorcelate.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new ConjureNextTurnPower(1));
    }

    public void upp() {
        upgradeBlock(3);
    }
}