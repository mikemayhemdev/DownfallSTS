package automaton.cards;

import automaton.AutomatonMod;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static sneckomod.SneckoMod.getRandomStatus;

public class FullRelease extends AbstractBronzeCard {

    public final static String ID = makeID("FullRelease");

    //stupid intellij stuff attack, enemy, rare


    public FullRelease() {
        super(ID, 3, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            //TODO Vex madness to make a function into a power
            //TODO We didn't plan for powers when doing art splits.  Pushed a FunctionPower card art - just use that for the compiled power card.
            //TODO We could immedaitely give the buff, but it feels right for balance to still have costs matter.
            //TODO The function will have to skip its normal use() effects when played, but still show them on the card, so the text reads:
            //TODO "Activate these effects at the start of each turn:"
            //TODO Either that, or the Power card actually does do its use() and targets enemies, which is also weird.
            //TODO Bonus question - should it work with Sentient Form?  buffing it EVERY TURN?!!?!?
        }
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}