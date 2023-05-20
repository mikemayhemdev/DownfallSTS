package collector.cards;

import collector.powers.NextTurnDrawFromCollectionPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class Summoning extends AbstractCollectorCard {
    public final static String ID = makeID(Summoning.class.getSimpleName());
    // intellij stuff skill, self, common, , , 12, , 1, 1

    public Summoning() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 12;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new NextTurnDrawFromCollectionPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
        uDesc();
    }
}