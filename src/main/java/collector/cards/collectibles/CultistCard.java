package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class CultistCard extends AbstractCollectibleCard {
    public final static String ID = makeID(CultistCard.class.getSimpleName());
    // intellij stuff power, self, common, , , , , , 

    public CultistCard() {
        super(ID, 2, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new RitualPower(p, 1, true));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}