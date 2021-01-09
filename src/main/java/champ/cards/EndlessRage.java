package champ.cards;

import champ.powers.WorseRupturePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class EndlessRage extends AbstractChampCard {

    public final static String ID = makeID("EndlessRage");

    //stupid intellij stuff power, self, uncommon

    public EndlessRage() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

      //  this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new WorseRupturePower(1));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}