package downfall.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.PanicButton;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.NoBlockPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import downfall.actions.ForceWaitAction;
import downfall.actions.MerchantThrowGoldAction;
import downfall.downfallMod;
import downfall.powers.SoulStealPower;
import downfall.vfx.GainSingleSoulEffect;
import downfall.vfx.SoulStealEffect;

/*
First Turn - Panic Button

Second Turn - Throw Coins 2x5

Third Turn - Escape, gain 1 strength

Strength and HP retains from fight to fight

On death, gain 100 Souls, 1 random Cards, 1 colorless card, 1 random Relic, 1 Potion.


Act 3 Event with a Shopkeeper boss using colorless cards, only if you killed him.  Neow revives him.

Bandage Up
Blind
Dramatic Entrance
Good Instincts
Panacea
Panic Button
Swift Strike
Trip
Apotheosis
Hand of Greed
Magnetism
Mayhem
Panache
Sadistic Nature
The Bomb
Evil Mode villain cards
 */

public class FleeingMerchant extends AbstractMonster {
    public static final String ID = downfallMod.makeID("FleeingMerchant");
    public static final String NAME = CardCrawlGame.languagePack.getUIString("RunHistoryPathNodes").TEXT[8];
    public static final String[] DIALOG = {

    };
    public static final String PANICBUTTONNAME = CardCrawlGame.languagePack.getCardStrings(PanicButton.ID).NAME;
    public static final String SOULSTEALNAME = CardCrawlGame.languagePack.getMonsterStrings(ID).MOVES[0];
    public static final float DRAW_X = Settings.WIDTH * 0.5F + 34.0F * Settings.scale;
    public static final float DRAW_Y = AbstractDungeon.floorY - 109.0F * Settings.scale;
    private static final int START_HP = 400;

    public static int CURRENT_HP = 400;
    public static int CURRENT_STRENGTH = 0;
    public static int CURRENT_SOULS = 0;

    public static boolean DEAD = false;
    public static boolean ESCAPED = false;

    // Move bytes
    private static byte ATTACK = 0;
    private static byte DEFEND = 1;
    private static byte ESCAPE = 2;
    private static byte SOULSTEAL = 3;

    private int turn = 0;
    private boolean boss = false;

    public FleeingMerchant() {
        super(NAME, ID, START_HP, -10.0F, -30.0F, 180.0F, 150.0F, null, 0.0F, 0.0F);


        drawX = 1260.0F * Settings.scale;
        drawY = 370.0F * Settings.scale;

        loadAnimation("images/npcs/merchant/skeleton.atlas", "images/npcs/merchant/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(1.0F);

        type = EnemyType.NORMAL;
        dialogX = -200.0F * Settings.scale;
        dialogY = 10.0F * Settings.scale;

        gold = 100;
        halfDead = false;

        damage.add(new DamageInfo(this, 2));
        setHp(400);
        this.currentHealth = CURRENT_HP;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!boss) {
            if (!isDeadOrEscaped() || AbstractDungeon.getCurrRoom().cannotLose) {
                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.MERCHANT_RUG_IMG, DRAW_X, DRAW_Y, 512.0F * Settings.scale, 512.0F * Settings.scale);
            }
        }

        super.render(sb);
    }

    @Override
    public void usePreBattleAction() {
        AbstractDungeon.getCurrRoom().eliteTrigger = true;
        //AbstractDungeon.getCurrRoom().cannotLose = true;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));

        //AbstractDungeon.actionManager.addToTop(new TalkAction(this, (abuse >= 3 ? DIALOG[2] : DIALOG[0]), 0.5F, 3.0F));

        if (CURRENT_STRENGTH > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, CURRENT_STRENGTH), CURRENT_STRENGTH));
        }
        if (CURRENT_SOULS > 0) {
            this.addToBot(new ApplyPowerAction(this, this, new SoulStealPower(this, CURRENT_SOULS), CURRENT_SOULS));
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                ESCAPED = false;
            }
        });
    }

    @Override
    public void takeTurn() {
        if (nextMove == ESCAPE) {
            CURRENT_HP = this.currentHealth;
            AbstractDungeon.getCurrRoom().mugged = true;
            FleeingMerchant.ESCAPED = true;
            this.addToBot(new CanLoseAction());
            this.addToBot(new VFXAction(new SmokeBombEffect(hb.cX, hb.cY)));
            this.addToBot(new EscapeAction(this));
            this.addToBot(new SetMoveAction(this, ESCAPE, Intent.ESCAPE));

            int roll = MathUtils.random(2);
            if (roll == 0) {
                CardCrawlGame.sound.play("VO_MERCHANT_KA");
            } else if (roll == 1) {
                CardCrawlGame.sound.play("VO_MERCHANT_KB");
            } else {
                CardCrawlGame.sound.play("VO_MERCHANT_KC");
            }
        } else if (nextMove == ATTACK) {
            this.addToBot(new MerchantThrowGoldAction(AbstractDungeon.player, this, 5, false));
            this.addToBot(new ForceWaitAction(1.6f));
            for (int i = 0; i < 5; ++i) {
                this.addToBot(new DamageAction(AbstractDungeon.player, damage.get(0), true));
            }

            int roll = MathUtils.random(2);
            if (roll == 0) {
                CardCrawlGame.sound.play("VO_MERCHANT_MA");
            } else if (roll == 1) {
                CardCrawlGame.sound.play("VO_MERCHANT_MB");
            } else {
                CardCrawlGame.sound.play("VO_MERCHANT_MC");
            }
        } else if (nextMove == DEFEND) {
            this.addToBot(new GainBlockAction(this, this, 30));
            this.addToBot(new ApplyPowerAction(this, this, new NoBlockPower(this, 1, true), 1));


            int roll = MathUtils.random(2);
            if (roll == 0) {
                CardCrawlGame.sound.play("VO_MERCHANT_2A");
            } else if (roll == 1) {
                CardCrawlGame.sound.play("VO_MERCHANT_2B");
            } else {
                CardCrawlGame.sound.play("VO_MERCHANT_2C");
            }
        } else if (nextMove == SOULSTEAL) {
            this.addToBot((new VFXAction(new SoulStealEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX, this.hb.cY), 0.5F)));

            this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
            CURRENT_STRENGTH += 1;
            if (AbstractDungeon.player.gold >= 15) {
                this.addToBot(new ApplyPowerAction(this, this, new SoulStealPower(this, 15), 15));
                CURRENT_SOULS += 15;
            } else {
                this.addToBot(new ApplyPowerAction(this, this, new SoulStealPower(this, AbstractDungeon.player.gold), AbstractDungeon.player.gold));
                CURRENT_SOULS += AbstractDungeon.player.gold;
            }
            AbstractDungeon.player.gold -= Math.min(AbstractDungeon.player.gold, 15);

            int roll = MathUtils.random(2);
            if (roll == 0) {
                CardCrawlGame.sound.play("VO_MERCHANT_3A");
            } else if (roll == 1) {
                CardCrawlGame.sound.play("VO_MERCHANT_3B");
            } else {
                CardCrawlGame.sound.play("VO_MERCHANT_3C");
            }
        }

        if (turn >= 0) {
            ++turn;
        }
        this.addToBot(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        if (turn == 0) {
            setMove(PANICBUTTONNAME, DEFEND, Intent.DEFEND);
            return;
        }
        if (turn == 1) {
            setMove(ATTACK, Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base, 5, true);
            return;
        }
        if (turn == 2) {
            setMove(SOULSTEALNAME, SOULSTEAL, Intent.BUFF);
            return;
        }
        if (turn == 3) {
            setMove(ESCAPE, Intent.ESCAPE);
            return;
        }
    }

    @Override
    public void die() {
        /*
        AbstractDungeon.getCurrRoom().rewardAllowed = false;
        AbstractDungeon.getCurrRoom().rewards.clear();
        */
        int increaseGold = 300;
        if (FleeingMerchant.CURRENT_SOULS > 0)
            increaseGold += FleeingMerchant.CURRENT_SOULS;

        AbstractDungeon.player.gainGold(increaseGold);
        CardCrawlGame.sound.playA("GOLD_JINGLE", -0.1F);
        CardCrawlGame.sound.playA("GOLD_JINGLE", .1F);
        for (int i = 0; i < increaseGold; i++) {
            AbstractDungeon.effectList.add(new GainSingleSoulEffect(this, this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
        }
        super.die();
        this.deathTimer += ((0.01F * increaseGold) - 1F);
        DEAD = true;
        helpDied = true;
    }

    @Override
    public void escape() {
        super.escape();
    }

    public static boolean helpEscaped = false;
    public static boolean helpDied = false;


    @Override
    public void update() {
        super.update();
        if (escaped && !ESCAPED) {
            ESCAPED = true;
            helpEscaped = true;
        }
    }

    /*
    @Override
    public void dispose() {
        AbstractDungeon.player.releaseCard();
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        //AbstractDungeon.combatRewardScreen.open();

        if (DEAD) {
            AbstractDungeon.getCurrRoom().endBattle();

            AbstractRoom tRoom = new HeartShopRoom(false);
            AbstractDungeon.currMapNode.setRoom(tRoom);
            AbstractDungeon.scene.nextRoom(tRoom);
            CardCrawlGame.fadeIn(1.5F);
            AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
            tRoom.onPlayerEntry();
            AbstractDungeon.player.hand.clear();
            AbstractDungeon.player.drawPile.clear();
            AbstractDungeon.player.limbo.clear();
            AbstractDungeon.player.discardPile.clear();
            AbstractDungeon.player.exhaustPile.clear();
            AbstractDungeon.closeCurrentScreen();

        }
        super.dispose();
    }
    */
}