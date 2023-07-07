package collector.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

import static collector.util.Wiz.atb;

public class OmenPower extends AbstractCollectorPower {
    public static final String NAME = "Omen";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public OmenPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    // Thanks Bard

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
            AbstractPower sourcePower = this;
            for (AbstractMonster tar : targets) {
                atb(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (tar.hasPower(WeakPower.POWER_ID) && tar.hasPower(VulnerablePower.POWER_ID) && !tar.isDeadOrEscaped()) {
                            sourcePower.flash();
                            addToBot(new ReducePowerAction(tar, owner, WeakPower.POWER_ID, 1));
                            addToBot(new ReducePowerAction(tar, owner, VulnerablePower.POWER_ID, 1));
                            addToBot(new LoseHPAction(tar, owner, toDamage));
                        }
                        isDone = true;
                    }
                });
            }
        }
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}