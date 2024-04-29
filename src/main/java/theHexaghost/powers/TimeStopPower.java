package theHexaghost.powers;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.GainPowerEffect;
import theHexaghost.HexaMod;

import java.util.ArrayList;
import java.util.Map;

public class TimeStopPower extends AbstractPower {
    public static final String POWER_ID = HexaMod.makeID("TimeStopPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private float timer;

    private Map<AbstractMonster, Float> timeScales;
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
    public void playApplyPowerSfx() {
        //to prevent the visual reminder below noising all the time
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if (this.timer <= 0F){
            ArrayList<AbstractGameEffect> effect2 = ReflectionHacks.getPrivate(this, AbstractPower.class, "effect");
            effect2.add(new GainPowerEffect(this));
            this.timer = 1F;
        } else {
            this.timer -= Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void updateDescription() {
        if(amount == 1){
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
        AbstractDungeon.actionManager.addToBottom(new SkipEnemiesTurnAction());
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            reducePower(1);
        }
        updateDescription();
        AbstractDungeon.onModifyPower();
        if (this.amount == 0){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, ID));
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
