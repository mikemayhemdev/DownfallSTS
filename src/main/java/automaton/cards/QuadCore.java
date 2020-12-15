package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BerserkPower;
import com.megacrit.cardcrawl.powers.DrawPower;

public class QuadCore extends AbstractBronzeCard {

    public final static String ID = makeID("QuadCore");

    //stupid intellij stuff power, self, rare

    public QuadCore() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(AutomatonMod.BURNOUT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainEnergyAction(1));
        applyToSelf(new BerserkPower(p, 1));
    }

    public void upp() {
        tags.remove(AutomatonMod.BURNOUT);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}