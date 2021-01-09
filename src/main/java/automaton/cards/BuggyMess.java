package automaton.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class BuggyMess extends AbstractBronzeCard {

    public final static String ID = makeID("BuggyMess");

    //stupid intellij stuff skill, self, uncommon

    public BuggyMess() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        thisEncodes();
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        shuffleIn(SneckoMod.getRandomStatus());
        atb(new GainEnergyAction(magicNumber));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}