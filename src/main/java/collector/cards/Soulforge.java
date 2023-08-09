
package collector.cards;

import collector.actions.DrawAndRemoveMegatherealFromCollectedCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class Soulforge extends AbstractCollectorCard {
    public final static String ID = makeID(Soulforge.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , 8, 3, , 

    public Soulforge() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            atb(new DrawAndRemoveMegatherealFromCollectedCardAction());
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
        uDesc();
    }
}

