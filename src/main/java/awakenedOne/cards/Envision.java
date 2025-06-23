package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class Envision extends AbstractAwakenedCard {
    public final static String ID = makeID(Envision.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Envision() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 4;
        this.tags.add(AwakenedOneMod.DELVE);
        loadJokeCardImage(this, ID+".png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new ConjureAction(false, true));
    }

    public void upp() {
        upgradeBlock(3);
    }
}