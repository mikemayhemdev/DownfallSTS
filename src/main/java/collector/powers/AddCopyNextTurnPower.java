package collector.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static collector.util.Wiz.atb;
import static collector.util.Wiz.makeInHand;

public class AddCopyNextTurnPower extends AbstractCollectorPower implements NonStackablePower {
    public static final String NAME = "AddCopyNextTurn";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private AbstractCard tar;

    public AddCopyNextTurnPower(AbstractCard tar) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, -1);
        canGoNegative = false;
        this.tar = tar;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        makeInHand(tar.makeStatEquivalentCopy());
        atb(new RemoveSpecificPowerAction(owner, owner, this));
    }
}