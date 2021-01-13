package downfall.monsters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import downfall.downfallMod;
import downfall.powers.DrawReductionPowerPlus;

import java.util.ArrayList;

public class FaceTrader extends AbstractMonster {

    public static final String ID = downfallMod.makeID("FaceTrader");
    public static final String NAME = CardCrawlGame.languagePack.getEventString("FaceTrader").NAME;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 225.0F;
    private static final float HB_H = 250.0F;

    int turnNum = 0;

    public FaceTrader() {
        super(NAME, ID, 100, HB_X, HB_Y, HB_W, HB_H, "downfallResources/images/monsters/facetrader/facetrader.png");
        switch (AbstractDungeon.actNum) {
            case 1:
                setHp(150);
                break;
            case 2:
                setHp(200);
                break;
            case 3:
                setHp(250);
                break;
        }

        loadAnimation("downfallResources/images/monsters/facetrader/facetrader.atlas", "downfallResources/images/monsters/facetrader/facetrader.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        this.stateData.setMix("Hit", "Idle", 0.2F);
        this.stateData.setMix("AttackGremlin", "Idle", 0.2F);
        this.stateData.setMix("AttackNloth", "Idle", 0.2F);
        this.stateData.setMix("AttackCleric", "Idle", 0.2F);
        this.stateData.setMix("AttackCultist", "Idle", 0.2F);
        this.stateData.setMix("AttackSerpent", "Idle", 0.2F);

        this.damage.add(new DamageInfo(this, 15));
        this.damage.add(new DamageInfo(this, 10));
        this.type = EnemyType.ELITE;
    }

    @Override
    public void changeState(String stateName) {
        switch(stateName) {
            case "AttackGremlin":
                this.state.setAnimation(0, "AttackGremlin", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case "AttackNloth":
                this.state.setAnimation(0, "AttackNloth", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case "AttackCleric":
                this.state.setAnimation(0, "AttackCleric", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case "AttackCultist":
                this.state.setAnimation(0, "AttackCultist", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case "AttackSerpent":
                this.state.setAnimation(0, "AttackSerpent", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
        }

    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
                addToBot(new MakeTempCardInDrawPileAction(new Doubt(), 1, true, false));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "AttackSerpent"));

                break;
            case 2:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 2, false), 2));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "AttackGremlin"));
                break;
            case 3:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawReductionPowerPlus(AbstractDungeon.player, 2), 2));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "AttackNloth"));
                break;
            case 4:
                addToBot(new GainBlockAction(this, 10));
                addToBot(new ApplyPowerAction(this, this, new RitualPower(this, 1, false), 1));
                if (!hasPower(RitualPower.POWER_ID)){
                    addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
                }
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "AttackCultist"));
                break;
            case 5:
                addToBot(new GainBlockAction(this, 10));
                addToBot(new HealAction(this, this, 10));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "AttackCleric"));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        int bruh;
        switch (turnNum) {
            case 0:
                bruh = AbstractDungeon.cardRandomRng.random(0,2);
                turnNum = 1;
                switch (bruh) {
                    case 0: {
                        this.setMove((byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
                        break;
                    }
                    case 1: {
                        this.setMove((byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
                        break;
                    }
                    case 2: {
                        this.setMove((byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
                        break;
                    }
                }
                break;
            case 1:
                bruh = AbstractDungeon.cardRandomRng.random(0,1);
                turnNum = 0;
                switch (bruh) {
                    case 0: {
                        this.setMove((byte) 4, Intent.DEFEND_BUFF);
                        break;
                    }
                    case 1: {
                        this.setMove((byte) 5, Intent.DEFEND_BUFF);
                        break;
                    }
                }
                break;
        }
    }

}
