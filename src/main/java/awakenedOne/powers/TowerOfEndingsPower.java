package awakenedOne.powers;

import awakenedOne.actions.KillEnemyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.util.Wiz.*;

public class TowerOfEndingsPower extends AbstractAwakenedPower implements OnAwakenPower {

    private static final int NEEDED_AWAKEN = 12;

    // intellij stuff buff
    public static final String NAME = TowerOfEndingsPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public TowerOfEndingsPower() {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, -1);
        canGoNegative = false;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + NEEDED_AWAKEN + DESCRIPTIONS[1];
    }

    @Override
    public void onAwaken(int amount) {
        int currentAwokenness = awakenedAmt();
        if (currentAwokenness + amount == NEEDED_AWAKEN) {
            flash();
            forAllMonstersLiving(q -> atb(new KillEnemyAction(q)));
        }
    }
}