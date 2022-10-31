package automaton.cards;

import automaton.AutomatonMod;
import automaton.powers.DefaultPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ClassDefault extends AbstractBronzeCard {
    public final static String ID = makeID("ClassDefault");

    public ClassDefault() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("ClassDefault.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DefaultPower(magicNumber));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}