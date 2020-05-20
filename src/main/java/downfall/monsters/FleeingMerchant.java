package downfall.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import downfall.actions.ForceWaitAction;
import downfall.actions.LoseGoldAction;
import downfall.actions.MerchantThrowGoldAction;
import downfall.powers.SoulStealPower;
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
    public static final String ID = "downfall:FleeingMerchant";
    public static final String NAME = CardCrawlGame.languagePack.getUIString("RunHistoryPathNodes").TEXT[8];
    public static final String[] DIALOG = {

    };
    public static final String PANICBUTTONNAME = CardCrawlGame.languagePack.getCardStrings(PanicButton.ID).NAME;
    public static final String SOULSTEALNAME = CardCrawlGame.languagePack.getMonsterStrings(ID).MOVES[0];
    public static final float DRAW_X = Settings.WIDTH * 0.5F + 34.0F * Settings.scale;
    public static final float DRAW_Y = AbstractDungeon.floorY - 109.0F * Settings.scale;
    private static final int START_HP = 500;

    public static int CURRENT_HP = 500;
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

        type = EnemyType.BOSS;
        dialogX = -200.0F * Settings.scale;
        dialogY = 10.0F * Settings.scale;

        gold = 100;
        halfDead = false;

        damage.add(new DamageInfo(this, 2));
        setHp(500);
        this.currentHealth = CURRENT_HP;

        ESCAPED = false;
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
        AbstractDungeon.getCurrRoom().cannotLose = true;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));

        //AbstractDungeon.actionManager.addToTop(new TalkAction(this, (abuse >= 3 ? DIALOG[2] : DIALOG[0]), 0.5F, 3.0F));

        if (CURRENT_STRENGTH > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, CURRENT_STRENGTH), CURRENT_STRENGTH));
        }
        if (CURRENT_SOULS > 0) {
            this.addToBot(new ApplyPowerAction(this, this, new SoulStealPower(this, CURRENT_SOULS), CURRENT_SOULS));
        }
    }

    @Override
    public void takeTurn() {
        if (nextMove == ESCAPE) {
            CURRENT_HP = this.currentHealth;
            if (hasPower(StrengthPower.POWER_ID)) {
                CURRENT_STRENGTH = getPower(StrengthPower.POWER_ID).amount;
            }
            AbstractDungeon.getCurrRoom().smoked = true;
            this.addToBot(new CanLoseAction());
            this.addToBot(new VFXAction(new SmokeBombEffect(hb.cX, hb.cY)));
            this.addToBot(new EscapeAction(this));
            this.addToBot(new SetMoveAction(this, ESCAPE, Intent.ESCAPE));
        } else if (nextMove == ATTACK) {
            this.addToBot(new MerchantThrowGoldAction(AbstractDungeon.player, this, 5, false));
            this.addToBot(new ForceWaitAction(1.6f));
            for (int i = 0; i < 5; ++i) {
                this.addToBot(new DamageAction(AbstractDungeon.player, damage.get(0), true));
            }
        } else if (nextMove == DEFEND) {
            this.addToBot(new GainBlockAction(this, this, 30));
            this.addToBot(new ApplyPowerAction(this, this, new NoBlockPower(this, 1, true), 1));
        } else if (nextMove == SOULSTEAL) {
            this.addToBot((new VFXAction(new SoulStealEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX, this.hb.cY), 0.5F)));

            this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
            if (AbstractDungeon.player.gold >= 15) {
                this.addToBot(new ApplyPowerAction(this, this, new SoulStealPower(this, 15), 15));
                CURRENT_SOULS += 15;
            } else {
                this.addToBot(new ApplyPowerAction(this, this, new SoulStealPower(this, AbstractDungeon.player.gold), AbstractDungeon.player.gold));
                CURRENT_SOULS += AbstractDungeon.player.gold;
            }
            this.addToBot(new LoseGoldAction(15));

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
        AbstractDungeon.getCurrRoom().rewardAllowed = true;
        AbstractDungeon.getCurrRoom().rewards.clear();
        if (FleeingMerchant.CURRENT_SOULS > 0)
            AbstractDungeon.getCurrRoom().addStolenGoldToRewards(FleeingMerchant.CURRENT_SOULS);
        AbstractDungeon.getCurrRoom().addGoldToRewards(50);
        AbstractDungeon.getCurrRoom().addCardToRewards();
        AbstractDungeon.getCurrRoom().addCardToRewards();
        AbstractDungeon.getCurrRoom().addCardReward(new RewardItem(AbstractCard.CardColor.COLORLESS));
        AbstractDungeon.getCurrRoom().addPotionToRewards();
        AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.SHOP);
        super.die();
        DEAD = true;
    }

    @Override
    public void escape() {
        super.escape();
        ESCAPED = true;
    }

    @Override
    public void update() {
        super.update();
        if (escaped) {
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.combatRewardScreen.open();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.combatRewardScreen.open();
    }
}