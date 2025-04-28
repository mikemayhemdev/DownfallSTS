package collector.cards.collectibles;

import collector.powers.AddCopyNextTurnPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class GiantHeadCardStageTwo extends AbstractCollectibleCard {
    public final static String ID = makeID(GiantHeadCardStageTwo.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public GiantHeadCardStageTwo() {
        //TODO - does this need to be a Colorless, not a collectible?
        super(ID, 2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        cardsToPreview = new GiantHeadCardStageThree();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new AddCopyNextTurnPower(new GiantHeadCardStageThree()));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}