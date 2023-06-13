package collector.powers.collectioncards;

import collector.powers.AbstractCollectorPower;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static collector.util.Wiz.att;

public class ReptomancerCardPower extends AbstractCollectorPower {
    public static final String NAME = "ReptomancerCard";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public ReptomancerCardPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (TempHPField.tempHp.get(owner) > 0) {
            flash();
            int toApply = this.amount;
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractMonster tar = AbstractDungeon.getRandomMonster();
                    att(new ApplyPowerAction(tar, owner, new PoisonPower(tar, owner, toApply), toApply));
                }
            });
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}