package collector.cards.collectibles;

import collector.powers.collectioncards.GremlinNobCardPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class GremlinNobCard extends AbstractCollectibleCard {
    public final static String ID = makeID(GremlinNobCard.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , , 

    public GremlinNobCard() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GremlinNobCardPower(magicNumber));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}