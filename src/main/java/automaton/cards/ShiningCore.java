package automaton.cards;

import automaton.AutomatonMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShiningCore extends AbstractBronzeCard {

    public final static String ID = makeID("ShiningCore");

    //stupid intellij stuff skill, self, rare

    public ShiningCore() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        tags.add(AutomatonMod.CORE);
        tags.add(AutomatonMod.BURNOUT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainEnergyAction(1));
        if (inFire) {
            atb(new StunMonsterAction(m, p));
        }
    }

    public void upp() {
        upgradeBaseCost(2);
    }
}