package automaton.cards;

import automaton.AutomatonMod;
import automaton.powers.FireBonusPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DupeCore extends AbstractBronzeCard {

    public final static String ID = makeID("DupeCore");

    //stupid intellij stuff skill, self, uncommon

    public DupeCore() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(AutomatonMod.CORE);
        tags.add(AutomatonMod.BURNOUT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainEnergyAction(1));
        applyToSelf(new FireBonusPower(1));
    }

    public void upp() {
        tags.remove(AutomatonMod.BURNOUT);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}