package automaton.cards;

import automaton.powers.MaxOutputPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MaxOutput extends AbstractBronzeCard {

    public final static String ID = makeID("MaxOutput");

    //stupid intellij stuff power, self, uncommon

    public MaxOutput() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new Dazed();
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
        applyToSelf(new MaxOutputPower(1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}