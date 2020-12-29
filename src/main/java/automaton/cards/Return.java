package automaton.cards;

import automaton.actions.EasyXCostAction;
import automaton.powers.ReturnPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Return extends AbstractBronzeCard {

    public final static String ID = makeID("Return");

    //stupid intellij stuff skill, self, uncommon

    public Return() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            if (effect > 0)
                applyToSelfTop(new ReturnPower(effect));
            return true;
        }));
        atb(new GainEnergyAction(1));
    }

    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}