package automaton.util;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.Iterator;

public class ProtoDonu extends AbstractMonster {
    public static final String ID = "Donu";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final int HP = 250;
    public static final int A_2_HP = 265;
    private static final byte BEAM = 0;
    private static final byte CIRCLE_OF_PROTECTION = 2;
    private static final int ARTIFACT_AMT = 2;
    private static final int BEAM_DMG = 10;
    private static final int BEAM_AMT = 2;
    private static final int A_2_BEAM_DMG = 12;
    private int beamDmg;
    private static final String CIRCLE_NAME;
    private static final int CIRCLE_STR_AMT = 3;
    private boolean isAttacking;

    public ProtoDonu() {
        super(NAME, "Donu", 250, 0.0F, -20.0F, 390.0F, 390.0F, (String)null, 100.0F, 20.0F);
        this.loadAnimation("images/monsters/theForest/donu/skeleton.atlas", "images/monsters/theForest/donu/skeleton.json", 1.0F);
        TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        this.stateData.setMix("Attack_2", "Idle", 0.1F);
        this.type = EnemyType.BOSS;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(265);
        } else {
            this.setHp(250);
        }

        if (AbstractDungeon.ascensionLevel >= 4) {
            this.beamDmg = 12;
        } else {
            this.beamDmg = 10;
        }

        this.damage.add(new DamageInfo(this, this.beamDmg));
        this.isAttacking = false;
    }

    public void changeState(String stateName) {
        byte var3 = -1;
        switch(stateName.hashCode()) {
        case 1941037640:
            if (stateName.equals("ATTACK")) {
                var3 = 0;
            }
        default:
            switch(var3) {
            case 0:
                this.state.setAnimation(0, "Attack_2", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
            default:
            }
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 19) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 3)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 2)));
        }

    }

    public void takeTurn() {
        switch(this.nextMove) {
        case 0:
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));

            for(int i = 0; i < 2; ++i) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.FIRE));
            }

            this.isAttacking = false;
            break;
        case 2:
            Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();

            while(var1.hasNext()) {
                AbstractMonster m = (AbstractMonster)var1.next();
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_DONU_DEFENSE"));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, 3), 3));
            }

            this.isAttacking = true;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (this.isAttacking) {
            this.setMove((byte)0, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, 2, true);
        } else {
            this.setMove(CIRCLE_NAME, (byte)2, Intent.BUFF);
        }

    }

    public void die() {
        super.die();
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            this.onBossVictoryLogic();
            UnlockTracker.hardUnlockOverride("DONUT");
            UnlockTracker.unlockAchievement("SHAPES");
        }

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Donu");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        CIRCLE_NAME = MOVES[0];
    }
}
