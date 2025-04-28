package collector.powers.collectioncards;

import basemod.helpers.CardModifierManager;
import collector.powers.AbstractCollectorPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.JAX;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import expansioncontent.cardmods.EtherealMod;

import static collector.util.Wiz.atb;

public class SentryPower extends AbstractCollectorPower {
    public static final String NAME = "SentryPower";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public SentryPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}