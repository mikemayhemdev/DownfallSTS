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
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        shuffleIn(SneckoMod.getRandomStatus());
    }

    @Override
    public void onCompileToChangeCost(AbstractCard function, boolean forGameplay) {
        if (function.cost > 1) {
            function.cost -= 1;
            function.costForTurn -= 1; //TODO: Reducing cost needs to be routed through a function eventually and centralized for all Function cost changing effects, to prevent weird stuff like X-funcs.
        }
    }

    public void upp() {
        // TODO: What was this upgrade?
    }
}