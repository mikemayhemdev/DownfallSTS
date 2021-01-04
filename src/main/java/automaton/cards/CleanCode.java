package automaton.cards;

import automaton.powers.CleanCodePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class CleanCode extends AbstractBronzeCard {

    public final static String ID = makeID("CleanCode");

    //stupid intellij stuff power, self, rare

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public CleanCode() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
      //  this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CleanCodePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}