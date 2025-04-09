package collector.cards.collectibles;

import basemod.helpers.CardModifierManager;
import collector.cardmods.CollectedCardMod;
import collector.powers.AddCopyNextTurnPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class GiantHeadCardStageOne extends AbstractCollectibleCard {
    public final static String ID = makeID(GiantHeadCardStageOne.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public GiantHeadCardStageOne() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        AbstractCard tar = new GiantHeadCardStageTwo();
        CardModifierManager.addModifier(tar, new CollectedCardMod());
        cardsToPreview = tar;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard tar = new GiantHeadCardStageTwo();
        CardModifierManager.addModifier(tar, new CollectedCardMod());
        applyToSelf(new AddCopyNextTurnPower(tar));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}