package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReflectionHacks extends AbstractBronzeCard {

    public final static String ID = makeID("ReflectionHacks");

    //stupid intellij stuff skill, self, uncommon

    public ReflectionHacks() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(AutomatonMod.SHIELD);
        tags.add(AutomatonMod.BURNOUT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new BetterDiscardPileToHandAction(1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}