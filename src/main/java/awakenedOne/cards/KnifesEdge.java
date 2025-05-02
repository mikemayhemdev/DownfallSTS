package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class KnifesEdge extends AbstractAwakenedCard {
    public final static String ID = makeID(KnifesEdge.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public KnifesEdge() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, magicNumber));
        this.addToBot(new LoseHPAction(p, p, secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}