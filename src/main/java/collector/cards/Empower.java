package collector.cards;

import automaton.actions.EasyXCostAction;
import collector.powers.StrengthOverTurnsPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelfTop;
import static collector.util.Wiz.atb;

public class Empower extends AbstractCollectorCard {
    public final static String ID = makeID(Empower.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 2, 1

    public Empower() {
        super(ID, -1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            applyToSelfTop(new StrengthOverTurnsPower(magicNumber, effect));
            return true;
        }));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}