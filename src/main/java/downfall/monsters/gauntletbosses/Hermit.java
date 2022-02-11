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
import com.megacrit.cardcrawl.powers.*;
import downfall.downfallMod;
import downfall.powers.DrawReductionPowerPlus;
import downfall.powers.gauntletpowers.MonsterVigor;
import downfall.powers.gauntletpowers.OnDeathEveryoneBuffer;
import downfall.powers.gauntletpowers.OnDeathEveryoneRuggedVuln;
import hermit.powers.Bruise;

public class Hermit extends AbstractMonster {

    public static final String ID = downfallMod.makeID("GauntletHermit");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("hermit:hermit").NAMES[0];
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

        this.damage.add(new DamageInfo(this, 6));
        this.damage.add(new DamageInfo(this, 6));
        this.damage.add(new DamageInfo(this, 6));
    }

    @Override
    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new OnDeathEveryoneRuggedVuln(this, 2), 2));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                if (hasPower(MonsterVigor.POWER_ID)) {
                    addToBot(new RemoveSpecificPowerAction(this, this, MonsterVigor.POWER_ID));
                }
                break;
            case 2:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new GainBlockAction(this, 5));
                if (hasPower(MonsterVigor.POWER_ID)) {
                    addToBot(new RemoveSpecificPowerAction(this, this, MonsterVigor.POWER_ID));
                }
                break;
            case 3:
                addToBot(new GainBlockAction(this, 10));
                break;
            case 4:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Bruise(AbstractDungeon.player, 5), 5));
                if (hasPower(MonsterVigor.POWER_ID)) {
                    addToBot(new RemoveSpecificPowerAction(this, this, MonsterVigor.POWER_ID));
                }
                break;
            case 5:
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 4), 4));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        turnNum ++;
        if (turnNum == 5) {
            setMove((byte)5, Intent.BUFF);
        }
        else {
            int rnd = AbstractDungeon.cardRandomRng.random(0, 3);
            switch (rnd) {
                case 0:
                    setMove((byte)1, Intent.ATTACK, this.damage.get(0).base, 2, true);
                    break;
                case 1:
                    setMove((byte)2, Intent.ATTACK_DEFEND, this.damage.get(1).base);
                    break;
                case 2:
                    setMove((byte)3, Intent.DEFEND);
                    break;
                case 3:
                    setMove((byte)4, Intent.ATTACK_DEBUFF, this.damage.get(2).base);
                    break;
            }
        }
    }


}
