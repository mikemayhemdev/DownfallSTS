package downfall.monsters;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Bone;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AwakenedEyeParticle;
import com.megacrit.cardcrawl.vfx.AwakenedWingParticle;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class AwakenedOneMirror extends AbstractMonster {
    private static final Logger logger = LogManager.getLogger(com.megacrit.cardcrawl.monsters.beyond.AwakenedOne.class.getName());
    public static final String ID = "AwakenedOne";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private boolean form1 = true;
    private boolean firstTurn = true;
    private boolean saidPower = false;
    public static final int STAGE_1_HP = 300;
    public static final int STAGE_2_HP = 300;
    public static final int A_9_STAGE_1_HP = 320;
    public static final int A_9_STAGE_2_HP = 320;
    private static final int A_4_STR = 2;
    private static final byte SLASH = 1;
    private static final byte SOUL_STRIKE = 2;
    private static final byte REBIRTH = 3;
    private static final String SS_NAME;
    private static final int SLASH_DMG = 20;
    private static final int SS_DMG = 6;
    private static final int SS_AMT = 4;
    private static final int REGEN_AMT = 10;
    private static final int STR_AMT = 1;
    private static final byte DARK_ECHO = 5;
    private static final byte SLUDGE = 6;
    private static final byte TACKLE = 8;
    private static final String DARK_ECHO_NAME;
    private static final String SLUDGE_NAME;
    private static final int ECHO_DMG = 40;
    private static final int SLUDGE_DMG = 18;
    private static final int TACKLE_DMG = 10;
    private static final int TACKLE_AMT = 3;
    private float fireTimer = 0.0F;
    private static final float FIRE_TIME = 0.1F;
    private Bone eye;
    private Bone back;
    private boolean animateParticles = false;
    private ArrayList<AwakenedWingParticle> wParticles = new ArrayList();

    public AwakenedOneMirror(float x, float y) {
        super(NAME, "AwakenedOne", 150, 40.0F, -30.0F, 460.0F, 250.0F, (String)null, x, y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(160);
        } else {
            this.setHp(150);
        }

        this.loadAnimation("images/monsters/theForest/awakenedOne/skeleton.atlas", "images/monsters/theForest/awakenedOne/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle_1", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle_1", 0.3F);
        this.stateData.setMix("Hit", "Idle_2", 0.2F);
        this.stateData.setMix("Attack_1", "Idle_1", 0.2F);
        this.stateData.setMix("Attack_2", "Idle_2", 0.2F);
        this.state.getData().setMix("Idle_1", "Idle_2", 1.0F);
        this.eye = this.skeleton.findBone("Eye");
        Iterator var4 = this.skeleton.getBones().iterator();

        while(var4.hasNext()) {
            Bone b = (Bone)var4.next();
            logger.info(b.getData().getName());
        }

        this.back = this.skeleton.findBone("Hips");
        this.type = EnemyType.BOSS;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        this.damage.add(new DamageInfo(this, 15));
        this.damage.add(new DamageInfo(this, 4));
        this.damage.add(new DamageInfo(this, 30));
        this.damage.add(new DamageInfo(this, 12));
        this.damage.add(new DamageInfo(this, 6));
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
        AbstractDungeon.getCurrRoom().cannotLose = true;
        if (AbstractDungeon.ascensionLevel >= 19) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, 10)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CuriosityPower(this, 2)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, 5)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CuriosityPower(this, 1)));
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new UnawakenedPower(this)));
        if (AbstractDungeon.ascensionLevel >= 4) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
        }

        UnlockTracker.markBossAsSeen("CROW");
    }

    public void takeTurn() {
        int i;
        label27:
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_AWAKENED_POUNCE"));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK_1"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                break;
            case 2:
                i = 0;

                while(true) {
                    if (i >= 4) {
                        break label27;
                    }

                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AbstractGameAction.AttackEffect.FIRE));
                    ++i;
                }
            case 3:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_AWAKENEDONE_1"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, true), 0.05F, true));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REBIRTH"));
            case 4:
            case 7:
            default:
                break;
            case 5:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK_2"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
                this.firstTurn = false;
                AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_AWAKENEDONE_3"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, new Color(0.1F, 0.0F, 0.2F, 1.0F), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, new Color(0.3F, 0.2F, 0.4F, 1.0F), ShockWaveEffect.ShockWaveType.CHAOTIC), 1.0F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(2), AbstractGameAction.AttackEffect.SMASH));
                break;
            case 6:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK_2"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(3), AbstractGameAction.AttackEffect.POISON));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, true));
                break;
            case 8:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_AWAKENED_ATTACK"));

                for(i = 0; i < 3; ++i) {
                    AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.06F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(4), AbstractGameAction.AttackEffect.FIRE, true));
                }
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void changeState(String key) {
        switch (key) {
            case "REBIRTH":
                if (AbstractDungeon.ascensionLevel >= 9) {
                    this.maxHealth = 160;
                } else {
                    this.maxHealth = 150;
                }

                if (Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
                    float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
                    this.maxHealth = (int)((float)this.maxHealth * mod);
                }

                if (ModHelper.isModEnabled("MonsterHunter")) {
                    this.currentHealth = (int)((float)this.currentHealth * 1.5F);
                }

                this.state.setAnimation(0, "Idle_2", true);
                this.halfDead = false;
                this.animateParticles = true;
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
                AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
                break;
            case "ATTACK_1":
                this.state.setAnimation(0, "Attack_1", false);
                this.state.addAnimation(0, "Idle_1", true, 0.0F);
                break;
            case "ATTACK_2":
                this.state.setAnimation(0, "Attack_2", false);
                this.state.addAnimation(0, "Idle_2", true, 0.0F);
        }

    }

    protected void getMove(int num) {
        if (this.form1) {
            if (this.firstTurn) {
                this.setMove((byte)1, Intent.ATTACK, 15);
                this.firstTurn = false;
                return;
            }

            if (num < 25) {
                if (!this.lastMove((byte)2)) {
                    this.setMove(SS_NAME, (byte)2, Intent.ATTACK, 4, 4, true);
                } else {
                    this.setMove((byte)1, Intent.ATTACK, 15);
                }
            } else if (!this.lastTwoMoves((byte)1)) {
                this.setMove((byte)1, Intent.ATTACK, 15);
            } else {
                this.setMove(SS_NAME, (byte)2, Intent.ATTACK, 4, 4, true);
            }
        } else {
            if (this.firstTurn) {
                this.setMove(DARK_ECHO_NAME, (byte)5, Intent.ATTACK, 30);
                return;
            }

            if (num < 50) {
                if (!this.lastTwoMoves((byte)6)) {
                    this.setMove(SLUDGE_NAME, (byte)6, Intent.ATTACK_DEBUFF, 12);
                } else {
                    this.setMove((byte)8, Intent.ATTACK, 6, 3, true);
                }
            } else if (!this.lastTwoMoves((byte)8)) {
                this.setMove((byte)8, Intent.ATTACK, 6, 3, true);
            } else {
                this.setMove(SLUDGE_NAME, (byte)6, Intent.ATTACK_DEBUFF, 12);
            }
        }

    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            if (this.form1) {
                this.state.addAnimation(0, "Idle_1", true, 0.0F);
            } else {
                this.state.addAnimation(0, "Idle_2", true, 0.0F);
            }
        }

        if (this.currentHealth <= 0 && !this.halfDead) {
            if (AbstractDungeon.getCurrRoom().cannotLose) {
                this.halfDead = true;
            }

            Iterator s = this.powers.iterator();

            AbstractPower p;
            while(s.hasNext()) {
                p = (AbstractPower)s.next();
                p.onDeath();
            }

            s = AbstractDungeon.player.relics.iterator();

            while(s.hasNext()) {
                AbstractRelic r = (AbstractRelic)s.next();
                r.onMonsterDeath(this);
            }

            this.addToTop(new ClearCardQueueAction());
            s = this.powers.iterator();

            while(true) {
                do {
                    if (!s.hasNext()) {
                        this.setMove((byte)3, Intent.UNKNOWN);
                        this.createIntent();
                        AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[0]));
                        AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, Intent.UNKNOWN));
                        this.applyPowers();
                        this.firstTurn = true;
                        this.form1 = false;
                        if (GameActionManager.turn <= 1) {
                            UnlockTracker.unlockAchievement("YOU_ARE_NOTHING");
                        }

                        return;
                    }

                    p = (AbstractPower)s.next();
                } while(p.type != AbstractPower.PowerType.DEBUFF && !p.ID.equals("Curiosity") && !p.ID.equals("Unawakened") && !p.ID.equals("Shackled"));

                s.remove();
            }
        }
    }

    public void update() {
        super.update();
        if (!this.isDying && this.animateParticles) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer < 0.0F) {
                this.fireTimer = 0.1F;
                AbstractDungeon.effectList.add(new AwakenedEyeParticle(this.skeleton.getX() + this.eye.getWorldX(), this.skeleton.getY() + this.eye.getWorldY()));
                this.wParticles.add(new AwakenedWingParticle());
            }
        }

        Iterator<AwakenedWingParticle> p = this.wParticles.iterator();

        while(p.hasNext()) {
            AwakenedWingParticle e = (AwakenedWingParticle)p.next();
            e.update();
            if (e.isDone) {
                p.remove();
            }
        }

    }

    public void render(SpriteBatch sb) {
        Iterator var2 = this.wParticles.iterator();

        AwakenedWingParticle p;
        while(var2.hasNext()) {
            p = (AwakenedWingParticle)var2.next();
            if (p.renderBehind) {
                p.render(sb, this.skeleton.getX() + this.back.getWorldX(), this.skeleton.getY() + this.back.getWorldY());
            }
        }

        super.render(sb);
        var2 = this.wParticles.iterator();

        while(var2.hasNext()) {
            p = (AwakenedWingParticle)var2.next();
            if (!p.renderBehind) {
                p.render(sb, this.skeleton.getX() + this.back.getWorldX(), this.skeleton.getY() + this.back.getWorldY());
            }
        }

    }

    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();
            this.useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            if (this.saidPower) {
                CardCrawlGame.sound.play("VO_AWAKENEDONE_2");
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5F, DIALOG[1], false));
                this.saidPower = true;
            }

            Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while(var1.hasNext()) {
                AbstractMonster m = (AbstractMonster)var1.next();
                if (!m.isDying && m instanceof Cultist) {
                    AbstractDungeon.actionManager.addToBottom(new EscapeAction(m));
                }
            }

            this.onBossVictoryLogic();
            UnlockTracker.hardUnlockOverride("CROW");
            UnlockTracker.unlockAchievement("CROW");
        }

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("AwakenedOne");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        SS_NAME = MOVES[0];
        DARK_ECHO_NAME = MOVES[1];
        SLUDGE_NAME = MOVES[3];
    }
}
