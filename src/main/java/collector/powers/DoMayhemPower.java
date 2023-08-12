package collector.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.util.Wiz;

public class DoMayhemPower extends AbstractCollectorPower {
    public static final String NAME = "DoMayhem";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private static final int REQUISITE_DOOM = 25;

    public DoMayhemPower() {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, 1);
    }

    @Override
    public void atStartOfTurn() {
        boolean willActivate = Wiz.getEnemies().stream().anyMatch(q -> Wiz.pwrAmt(q, DoomPower.POWER_ID) >= REQUISITE_DOOM);
        if (willActivate) {
            flash();
            for (int i = 0; i < this.amount; ++i) {
                this.addToBot(new AbstractGameAction() {
                    public void update() {
                        this.addToBot(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));
                        this.isDone = true;
                    }
                });
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + REQUISITE_DOOM + (amount > 1 ? (DESCRIPTIONS[2] + amount + DESCRIPTIONS[3]) : DESCRIPTIONS[1]);
    }
}