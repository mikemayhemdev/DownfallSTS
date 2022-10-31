package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.goodstatus.Daze;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static automaton.AutomatonMod.makeBetaCardPath;

public class BuggyMess extends AbstractBronzeCard {

    public final static String ID = makeID("BuggyMess");

    //stupid intellij stuff skill, self, uncommon

    public BuggyMess() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        thisEncodes();
        baseMagicNumber = magicNumber = 1;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("ChosenStrike.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        shuffleIn(new Dazed());
        atb(new GainEnergyAction(magicNumber));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}