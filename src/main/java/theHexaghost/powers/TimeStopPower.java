package theHexaghost.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.HexaMod;

import java.util.Map;

public class TimeStopPower extends AbstractPower {
    public static final String POWER_ID = HexaMod.makeID("TimeStopPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private Map<AbstractMonster, Float> timeScales;
    // Not used Not used Not used Not used Not used Not used Not used Not used Not used
    public TimeStopPower(AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = true;
        updateDescription();
        loadRegion("time");
    }

    @Override
    public void updateDescription() {
        if(amount == 0){
            description = DESCRIPTIONS[0];
        }else{
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

//    @Override
//    public void onInitialApplication() {
//        timeScales = new HashMap<>();
//        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
//            if (m.state != null) {
//                AnimationState.TrackEntry e = m.state.getCurrent(0);
//                if (e != null) {
//                    timeScales.put(m, e.getTimeScale());
//                    e.setTimeScale(0);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onRemove() {
//        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
//            if (m.state != null) {
//                AnimationState.TrackEntry e = m.state.getCurrent(0);
//                if (e != null) {
//                    if (timeScales.containsKey(m)) {
//                        e.setTimeScale(timeScales.get(m));
//                    }
//                }
//            }
//        }
//    }

    @Override
    public void atStartOfTurn() {
        if (amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SkipEnemiesTurnAction());
            reducePower(1);
            if (this.amount == 0){
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, ID));
            }
            updateDescription();
            AbstractDungeon.onModifyPower();
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            reducePower(1);
        }
    }

//    @Override
//    public void update(int slot) {
//        super.update(slot);
//
//        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
//            if (m.state != null) {
//                AnimationState.TrackEntry e = m.state.getCurrent(0);
//                if (e != null) {
//                    if (!timeScales.containsKey(m)) {
//                        timeScales.put(m, e.getTimeScale());
//                    }
//                    e.setTimeScale(0);
//                }
//            }
//        }
//    }
}
