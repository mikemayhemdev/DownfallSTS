package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class SpikerCard extends AbstractCollectibleCard {
    public final static String ID = makeID(SpikerCard.class.getSimpleName());
    // intellij stuff power, self, common, , , , , 5, 3

    public SpikerCard() {
        super(ID, 1, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ThornsPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(3);
    }
}