package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class ShelledParasiteCard extends AbstractCollectibleCard {
    public final static String ID = makeID(ShelledParasiteCard.class.getSimpleName());
    // intellij stuff power, self, common, , , , , 3, 2

    public ShelledParasiteCard() {
        super(ID, 1, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PlatedArmorPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}