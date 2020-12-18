package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.powers.ExhaustStatusesPower;

public class Repulsor extends AbstractBronzeCard {

    public final static String ID = makeID("Repulsor");

    //stupid intellij stuff power, self, uncommon

    public Repulsor() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ExhaustStatusesPower(p, p, 1));
    }

    public void upp() {
        isInnate = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}