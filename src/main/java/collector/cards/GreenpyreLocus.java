package collector.cards;

import collector.actions.SeekFromCollectionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class GreenpyreLocus extends AbstractCollectorCard {
    public final static String ID = makeID(GreenpyreLocus.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , 1, 1

    public GreenpyreLocus() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SeekFromCollectionAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
        uDesc();
    }
}