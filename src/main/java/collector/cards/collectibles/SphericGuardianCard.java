package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class SphericGuardianCard extends AbstractCollectibleCard {
    public final static String ID = makeID(SphericGuardianCard.class.getSimpleName());
    // intellij stuff power, self, common, , , , , , 

    public SphericGuardianCard() {
        super(ID, 2, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BarricadePower(p));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}