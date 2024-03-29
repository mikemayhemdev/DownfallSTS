package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.powers.ExhaustStatusesPower;
import sneckomod.SneckoMod;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Repulsor extends AbstractBronzeCard {

    public final static String ID = makeID("Repulsor");

    //stupid intellij stuff power, self, uncommon

    public Repulsor() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Repulsor.png"));
     //   this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ExhaustStatusesPower(p, p, 1));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}