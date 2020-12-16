package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Capacitor extends AbstractBronzeCard {

    public final static String ID = makeID("Capacitor");

    //stupid intellij stuff skill, self, uncommon

    public Capacitor() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(AutomatonMod.CORE);
        tags.add(AutomatonMod.BURNOUT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ReduceCostAction(this));
        atb(new GainEnergyAction(1));
    }

    public void upp() {
        isInnate = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}