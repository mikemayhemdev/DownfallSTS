package collector.cards;

import collector.powers.NextTurnReservePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class Bonfire extends AbstractCollectorCard {
    public final static String ID = makeID(Bonfire.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , 20, 5, , 

    public Bonfire() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 14;
        isPyre();
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new NextTurnReservePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}