//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction.TextType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_M;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_L;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_M;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.SplitPower;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.Iterator;

public class SpikeSlimeLDailyMod extends SpikeSlime_L {

    private static final MonsterStrings monsterStrings;

    public static final String NAME;
    public static final String[] MOVES;

    private static final String FRAIL_NAME;
    private static final String SPLIT_NAME;
    private float saveX;
    private float saveY;
    private boolean splitTriggered;

    public SpikeSlimeLDailyMod(float x, float y, int poisonAmount, int newHealth) {
        super(x, y, poisonAmount, newHealth);
    }

    public void takeTurn() {
        switch(this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), 2));
            case 2:
            default:
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());
                AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(this, 1.0F, 0.1F));
                AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(this));
                AbstractDungeon.actionManager.addToBottom(new SuicideAction(this, false));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0F));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("SLIME_SPLIT"));
                AbstractMonster mini1 = new SpikeSlime_M(saveX - 70F, -4F, 0,this.currentHealth);

                mini1.drawX = this.drawX +70F;

                mini1.usePreBattleAction();
                mini1.useUniversalPreBattleAction();
                AbstractMonster mini2 = new SpikeSlime_M(saveX + 70F, 4F, 0,this.currentHealth);

                mini2.drawX = this.drawX -70F;
                mini2.usePreBattleAction();
                mini2.useUniversalPreBattleAction();

                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(mini1, false));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(mini2, false));
                AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
                this.setMove(SPLIT_NAME, (byte)3, Intent.UNKNOWN);
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                if (AbstractDungeon.ascensionLevel >= 17) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 3, true), 3));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
                }
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }



    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SpikeSlime_L");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        FRAIL_NAME = MOVES[0];
        SPLIT_NAME = MOVES[1];
    }
}
