package automaton.cards;

import automaton.AutomatonMod;
import automaton.powers.OptimizePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Optimize extends AbstractBronzeCard {

    public final static String ID = makeID("Optimize");

    //stupid intellij stuff power, self, uncommon

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;

    public Optimize() {
        super(ID, 0, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Optimize.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new OptimizePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}