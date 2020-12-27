package automaton.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class BuggyMess extends AbstractBronzeCard {

    public final static String ID = makeID("BuggyMess");

    //stupid intellij stuff skill, self, uncommon

    public BuggyMess() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        thisEncodes();
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        shuffleIn(SneckoMod.getRandomStatus());
    }

    @Override
    public void onCompileLast(AbstractCard function, boolean forGameplay) {
        if (function.cost >= magicNumber) {
            function.cost -= magicNumber;
            function.costForTurn -= magicNumber; //TODO: Reducing cost needs to be routed through a function eventually and centralized for all Function cost changing effects, to prevent weird stuff like X-funcs.
        }
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}