package automaton.cards;

import automaton.actions.ScryEncodeCardsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class Assembly extends AbstractBronzeCard {

    public final static String ID = makeID("Assembly");

    //stupid intellij stuff skill, self, rare

    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 3;

    public Assembly() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
      //  this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ScryEncodeCardsAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}