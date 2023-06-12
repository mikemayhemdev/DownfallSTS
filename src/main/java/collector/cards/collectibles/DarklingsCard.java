package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DuplicationPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class DarklingsCard extends AbstractCollectibleCard {
    public final static String ID = makeID(DarklingsCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , , 

    public DarklingsCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DuplicationPower(p, 2));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}