package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AngerPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class GremlinNobCard extends AbstractCollectibleCard {
    public final static String ID = makeID(GremlinNobCard.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , , 

    public GremlinNobCard() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new AngerPower(p, 1));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}