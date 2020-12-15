package automaton.cards;

import automaton.MechaHelper;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PrepareGuns extends AbstractBronzeCard {

    public final static String ID = makeID("PrepareGuns");

    //stupid intellij stuff skill, self, uncommon

    public PrepareGuns() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = MechaHelper.blasters.size();
        atb(new GainEnergyAction(x));
    }

    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}