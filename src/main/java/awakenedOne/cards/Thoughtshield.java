package awakenedOne.cards;

import awakenedOne.ui.AwakenButton;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;

public class Thoughtshield extends AbstractAwakenedCard {
    public final static String ID = makeID(Thoughtshield.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , 14, 2, 1, 1

    public Thoughtshield() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 14;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        AwakenButton.awaken(magicNumber);
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}