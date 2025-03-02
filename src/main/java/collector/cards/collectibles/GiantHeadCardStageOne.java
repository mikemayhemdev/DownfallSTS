package collector.cards.collectibles;

import collector.powers.AddCopyNextTurnPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class GiantHeadCardStageOne extends AbstractCollectibleCard {
    public final static String ID = makeID(GiantHeadCardStageOne.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public GiantHeadCardStageOne() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new GiantHeadCardStageTwo();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        applyToSelf(new AddCopyNextTurnPower(new GiantHeadCardStageTwo()));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}