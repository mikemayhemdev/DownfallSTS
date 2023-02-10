package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Return extends AbstractBronzeCard {

    public final static String ID = makeID("Return");

    //stupid intellij stuff skill, self, uncommon

    public Return() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Return.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DiscardPileToTopOfDeckAction(p));
        applyToSelf(new EnergizedBluePower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}