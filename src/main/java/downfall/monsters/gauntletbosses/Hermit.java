package downfall.monsters.gauntletbosses;

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

public class Hermit extends AbstractMonster {

    public static final String ID = downfallMod.makeID("FaceTrader");
    public static final String NAME = CardCrawlGame.languagePack.getEventString("FaceTrader").NAME;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 225.0F;
    private static final float HB_H = 250.0F;

    int turnNum = 0;

    public Hermit(float x, float y) {
        super(NAME, ID, 72, 0.0F, -5.0F, 240.0F, 270.0F, null, x, y);
   this.loadAnimation("hermitResources/images/char/hermit/Hermit.atlas", "hermitResources/images/char/hermit/Hermit.json", 1.0f);
        this.flipHorizontal = true;
        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(0.7F);

        type = EnemyType.ELITE;

        this.damage.add(new DamageInfo(this, 1));
        this.damage.add(new DamageInfo(this, 1));
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
        }
    }

}
