package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Override extends AbstractBronzeCard {

    public final static String ID = makeID("Override");

    //stupid intellij stuff skill, self, rare

    public Override() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: Repeat a card in your hand
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}