package collector.cards;

import automaton.actions.EasyXCostAction;
import collector.powers.NextTurnReservePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.atb;

public class StashAway extends AbstractCollectorCard {
    public final static String ID = makeID(StashAway.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public StashAway() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            applyToSelf(new NextTurnReservePower(effect));
            return true;
        }));
    }

    public void upp() {
        upgradeBlock(3);
    }
}