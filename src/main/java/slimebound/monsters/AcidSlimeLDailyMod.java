//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_L;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_M;
import com.megacrit.cardcrawl.powers.WeakPower;

public class AcidSlimeLDailyMod extends AcidSlime_L {
    public static final String ID = "AcidSlime_L";
    public static final String NAME;
    public static final String[] MOVES;
    private static final MonsterStrings monsterStrings;
    private static final String WOUND_NAME;
    private static final String SPLIT_NAME;
    private static final String WEAK_NAME;

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("AcidSlime_L");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        WOUND_NAME = MOVES[0];
        SPLIT_NAME = MOVES[1];
        WEAK_NAME = MOVES[2];
    }

    private float saveX;
    private float saveY;
    private boolean splitTriggered;


    public AcidSlimeLDailyMod(float x, float y, int poisonAmount, int newHealth) {
        super(x, y, poisonAmount, newHealth);

    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_SLIME_ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), 2));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());
                AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(this, 1.0F, 0.1F));
                AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(this));
                AbstractDungeon.actionManager.addToBottom(new SuicideAction(this, false));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0F));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("SLIME_SPLIT"));
                AbstractMonster mini1 = new AcidSlime_M(saveX - 70F, -4F, 0, this.currentHealth);

                mini1.drawX = this.drawX + 70F;

                mini1.usePreBattleAction();
                mini1.useUniversalPreBattleAction();
                AbstractMonster mini2 = new AcidSlime_M(saveX + 70F, 4F, 0, this.currentHealth);

                mini2.drawX = this.drawX - 70F;
                mini2.usePreBattleAction();
                mini2.useUniversalPreBattleAction();

                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(mini1, false));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(mini2, false));
                AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
                this.setMove(SPLIT_NAME, (byte) 3, Intent.UNKNOWN);
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
        }

    }
}
