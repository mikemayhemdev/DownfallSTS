package collector.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;

public class AttacksApplyDoomPower extends AbstractCollectorPower {
    public static final String NAME = "AttacksApplyDoomPower";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public AttacksApplyDoomPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            ArrayList<AbstractMonster> targets;
            if (m != null) {
                targets = new ArrayList<>();
                targets.add(m);
            } else {
                targets = AbstractDungeon.getMonsters().monsters;
            }
            int toDamage = amount;
            flash();
            for (AbstractMonster tar : targets) {
                atb(new AbstractGameAction() {
                    @Override
                    public void update() {
                        applyToEnemy(tar, new DoomPower(tar, toDamage));
                        isDone = true;
                    }
                });
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        atb(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}