package collector.powers;

import collector.actions.AllEnemyLoseHPAction;
import collector.cards.collectibles.AbstractCollectibleCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OmenPower extends AbstractCollectorPower {
    public static final String NAME = "Omen";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public OmenPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    public void onAfterCardPlayed(AbstractCard card) {
        if (card instanceof AbstractCollectibleCard) {
            this.flash();
            addToBot(new AllEnemyLoseHPAction(amount));
        }
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}