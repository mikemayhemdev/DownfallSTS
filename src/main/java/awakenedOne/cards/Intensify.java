package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.powers.IntensifyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.atb;

public class Intensify extends AbstractAwakenedCard {
    public final static String ID = makeID(Intensify.class.getSimpleName());
    // intellij stuff skill, self, common, , , 10, 4, , 

    public Intensify() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        baseBlock = 5;
        this.tags.add(AwakenedOneMod.DELVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (upgraded) {
            atb(new ConjureAction(false));
        }
        applyToSelf(new IntensifyPower(1));
    }

    public void upp() {
        upgradeBlock(3);
    }
}