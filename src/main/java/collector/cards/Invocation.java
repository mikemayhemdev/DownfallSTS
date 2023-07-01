package collector.cards;

import collector.powers.NextTurnReservePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class Invocation extends AbstractCollectorCard {
    public final static String ID = makeID(Invocation.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , , 

    public Invocation() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 2;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new NextTurnReservePower(1));
    }

    public void upp() {
        upgradeBlock(3);
    }
}