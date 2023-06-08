package collector.powers;

import collector.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.util.Wiz.att;

public class DanseMacabrePower extends AbstractCollectorPower {
    public static final String NAME = "DanseMacabre";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public DanseMacabrePower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (Wiz.getEnemies().stream().anyMatch(q -> q.hasPower(WeakPower.POWER_ID) || q.hasPower(VulnerablePower.POWER_ID))) {
            flash();
        }
        Wiz.forAllMonstersLiving(q -> {
            if (q.hasPower(WeakPower.POWER_ID) && q.hasPower(VulnerablePower.POWER_ID)) {
                addToBot(new AnimateHopAction(q));
                addToBot(new LoseHPAction(q, owner, amount * 2));
            }
            else if (q.hasPower(WeakPower.POWER_ID) || q.hasPower(VulnerablePower.POWER_ID)) {
                addToBot(new AnimateHopAction(q));
                addToBot(new LoseHPAction(q, owner, amount));
            }
        });
    }
}