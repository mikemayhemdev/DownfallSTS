package automaton.cards;

import automaton.MechaHelper;
import automaton.actions.EasyXCostAction;
import automaton.actions.FireFromPileAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;

public class MachineFire extends AbstractBronzeCard {

    public final static String ID = makeID("MachineFire");

    //stupid intellij stuff skill, enemy, uncommon

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    public MachineFire() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EasyXCostAction(this, (effect, params) -> {
            att(new FireFromPileAction(MechaHelper.blasters, 1, effect+params[0]));
            return true;
        }, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}