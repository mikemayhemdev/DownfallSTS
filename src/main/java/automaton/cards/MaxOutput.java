package automaton.cards;

import automaton.powers.MaxOutputPower;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MaxOutput extends AbstractBronzeCard {

    public final static String ID = makeID("MaxOutput");

    //stupid intellij stuff power, self, uncommon

    public MaxOutput() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new Dazed();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new MaxOutputPower(1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}