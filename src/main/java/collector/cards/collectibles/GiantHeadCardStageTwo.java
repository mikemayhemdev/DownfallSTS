package collector.cards.collectibles;

import basemod.helpers.CardModifierManager;
import collector.cardmods.CollectedCardMod;
import collector.powers.AddCopyNextTurnPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class GiantHeadCardStageTwo extends AbstractCollectibleCard {
    public final static String ID = makeID(GiantHeadCardStageTwo.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public GiantHeadCardStageTwo() {
        super(ID, 2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        cardsToPreview = new GiantHeadCardStageThree();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard tar = new GiantHeadCardStageThree();
        CardModifierManager.addModifier(tar, new CollectedCardMod());
        applyToSelf(new AddCopyNextTurnPower(tar));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}