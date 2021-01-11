package champ.cards;

import champ.powers.ImprovisingPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class Improvising extends AbstractChampCard {

    public final static String ID = makeID("Improvising");

    //stupid intellij stuff power, self, rare

    public Improvising() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);

      //  this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ImprovisingPower(1));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}