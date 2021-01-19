package downfall.monsters;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.FastShakeAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
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
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.IntimidateEffect;
import downfall.powers.EnemyDemonFormPower;

public class SneckoMirror extends AbstractMonster {
    public static final String ID = "Snecko";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final byte GLARE = 1;
    private static final byte BITE = 2;
    private static final byte TAIL = 3;
    private static final int BITE_DAMAGE = 15;
    private static final int TAIL_DAMAGE = 8;
    private static final int A_2_BITE_DAMAGE = 18;
    private static final int A_2_TAIL_DAMAGE = 10;
    private int biteDmg;
    private int tailDmg;
    private static final int VULNERABLE_AMT = 2;
    private static final int HP_MIN = 114;
    private static final int HP_MAX = 120;
    private static final int A_2_HP_MIN = 120;
    private static final int A_2_HP_MAX = 125;
    private boolean firstTurn;

    public SneckoMirror() {
        this(0.0F, 0.0F);
    }

    public SneckoMirror(float x, float y) {
        super(NAME, "Snecko", 120, -30.0F, -20.0F, 310.0F, 305.0F, (String) null, x, y);
        this.firstTurn = true;
        this.loadAnimation("sneckomodResources/images/char/skeleton.atlas", "sneckomodResources/images/char/skeletonM.json", 1.0F);
        TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.8F);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(240);
        } else {
            this.setHp(220);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.biteDmg = 24;
            this.tailDmg = 16;
        } else {
            this.biteDmg = 20;
            this.tailDmg = 12;
        }


        this.damage.add(new DamageInfo(this, this.biteDmg));
        this.damage.add(new DamageInfo(this, this.tailDmg));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_SNECKO_GLARE"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntimidateEffect(this.hb.cX, this.hb.cY), 0.5F));
                AbstractDungeon.actionManager.addToBottom(new FastShakeAction(AbstractDungeon.player, 1.0F, 1.0F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ConfusionPower(AbstractDungeon.player)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new EnemyDemonFormPower(this, 1), 1));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK_2"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 50.0F) * Settings.scale, Color.CHARTREUSE.cpy()), 0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.NONE));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(1), AttackEffect.SLASH_DIAGONAL));
                if (AbstractDungeon.ascensionLevel >= 17) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                }

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void changeState(String stateName) {
        byte var3 = -1;
        switch (stateName.hashCode()) {
            case 1321368571:
                if (stateName.equals("ATTACK_2")) {
                    var3 = 1;
                }
                break;
            case 1941037640:
                if (stateName.equals("ATTACK")) {
                    var3 = 0;
                }
        }

        switch (var3) {
            case 0:
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case 1:
                this.state.setAnimation(0, "Attack_2", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    protected void getMove(int num) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(MOVES[0], (byte) 1, Intent.STRONG_DEBUFF);
        } else if (num < 40) {
            this.setMove(MOVES[1], (byte) 3, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(1)).base);
        } else {
            if (this.lastTwoMoves((byte) 2)) {
                this.setMove(MOVES[1], (byte) 3, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(1)).base);
            } else {
                this.setMove(MOVES[2], (byte) 2, Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base);
            }

        }
    }

    public void die() {
        super.die();
        CardCrawlGame.sound.play("SNECKO_DEATH");
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Snecko");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
