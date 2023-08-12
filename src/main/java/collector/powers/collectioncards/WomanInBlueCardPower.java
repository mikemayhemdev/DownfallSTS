package collector.powers.collectioncards;

import collector.powers.AbstractCollectorPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionSlot;

import static collector.util.Wiz.atb;

public class WomanInBlueCardPower extends AbstractCollectorPower {
    public static final String NAME = "WomanInBlueCard";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public WomanInBlueCardPower() {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, -1);
    }

    @Override
    public void onVictory() {
        flash();
        for (int i = 0; i < AbstractDungeon.player.potionSlots; i++) {
            AbstractDungeon.player.potions.set(i, new PotionSlot(i));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}