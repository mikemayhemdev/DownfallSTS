package champ.cards;

import champ.powers.IronFortressPower;
import champ.powers.WorseRupturePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import sneckomod.SneckoMod;

@CardIgnore
public class EndlessRage extends AbstractChampCard {

    public final static String ID = makeID("EndlessRage");

    //stupid intellij stuff power, self, uncommon

    public EndlessRage() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

      //  this.tags.add(SneckoMod.BANNEDFORSNECKO);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new IronFortressPower(magicNumber));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}