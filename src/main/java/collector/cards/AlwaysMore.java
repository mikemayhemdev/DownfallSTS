package collector.cards;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class AlwaysMore extends AbstractCollectorCard {
    public final static String ID = makeID(AlwaysMore.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , , 

    public AlwaysMore() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 12;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainGoldAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(4);
    }
}