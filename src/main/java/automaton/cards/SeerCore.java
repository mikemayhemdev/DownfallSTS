package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SeerCore extends AbstractBronzeCard {

    public final static String ID = makeID("SeerCore");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 1;

    public SeerCore() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(AutomatonMod.CORE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ScryAction(magicNumber));
        atb(new GainEnergyAction(1));
        atb(new DrawCardAction(1));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}