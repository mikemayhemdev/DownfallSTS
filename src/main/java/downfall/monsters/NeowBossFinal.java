package downfall.monsters;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.HeartMegaDebuffEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import downfall.actions.NeowGainMinionPowersAction;
import downfall.downfallMod;
import downfall.powers.NeowInvulnerablePower;
import downfall.powers.neowpowers.BlasphemersDemise;
import downfall.powers.neowpowers.EnergyThief;
import downfall.powers.neowpowers.FeedingFrenzy;
import downfall.powers.neowpowers.SeeingDoubleProduct;
import downfall.vfx.combat.FakeDeathScene;
import guardian.vfx.SmallLaserEffectColored;

import java.util.ArrayList;

public class NeowBossFinal extends AbstractMonster {

    public static final String ID = downfallMod.makeID("NeowBossFinal");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString(ID).NAMES[0];
    private static final float HB_X = -40.0F;
    private static final float HB_Y = -40.0F;
    private static final float HB_W = 700.0F;
    private static final float HB_H = 500.0F;


    private static final float EYE1_X = -130.0F * Settings.scale;
    private static final float EYE1_Y = -50F * Settings.scale;

    private static final float EYE2_X = -20.0F * Settings.scale;
    private static final float EYE2_Y = -50F * Settings.scale;

    private static final float EYE3_X = 80.0F * Settings.scale;
    private static final float EYE3_Y = -50F * Settings.scale;

    private static final float INTENT_X = -210.0F * Settings.scale;
    private static final float INTENT_Y = 280.0F * Settings.scale;

    private static final float DRAWX_OFFSET = 100F * Settings.scale;
    private static final float DRAWY_OFFSET = 30F * Settings.scale;

    private float baseDrawX;

    private int turnNum = 0;

    private int strAmt;
    private int blockAmt;

    public static NeowBossFinal neowboss;


    public NeowBossFinal() {
        super(NAME, ID, 600, HB_X, HB_Y, HB_W, HB_H, "images/npcs/neow/skeleton.png");

        this.loadAnimation("images/npcs/neow/skeleton.atlas", "images/npcs/neow/skeleton.json", 1.0F);

        this.drawX += DRAWX_OFFSET;
        this.drawY += DRAWY_OFFSET;

        type = EnemyType.BOSS;
        this.baseDrawX = drawX;


        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(650);
        } else {
            setHp(600);
        }


        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        this.damage.add(new DamageInfo(this, 6)); //Eye Beam Damage
        this.damage.add(new DamageInfo(this, 20));  //Scream Damage

        this.strAmt = 2; //Strength Scaling for growth ability
        this.blockAmt = 20; //Block for growth ability

        this.intentOffsetX = INTENT_X;


        // halfDead = true;

        //Initialize the boss list with the four


        if (AbstractDungeon.isPlayerInDungeon()) AbstractDungeon.scene = new FakeDeathScene();
    }

    public void curses() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartMegaDebuffEffect()));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 3, true), 3));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 3, true), 3));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 3, true), 3));

        ArrayList<AbstractCard> ac = downfallMod.getRandomDownfallCurse(4);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(ac.get(0).makeStatEquivalentCopy(), 1, true, false, false, (float) Settings.WIDTH * 0.2F, (float) Settings.HEIGHT / 2.0F));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(ac.get(1).makeStatEquivalentCopy(), 1, true, false, false, (float) Settings.WIDTH * 0.4F, (float) Settings.HEIGHT / 2.0F));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(ac.get(2).makeStatEquivalentCopy(), 1, true, false, false, (float) Settings.WIDTH * 0.6F, (float) Settings.HEIGHT / 2.0F));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(ac.get(3).makeStatEquivalentCopy(), 1, true, false, false, (float) Settings.WIDTH * 0.8F, (float) Settings.HEIGHT / 2.0F));

    }

    @Override
    public void usePreBattleAction() {

        NeowBoss.neowboss = null;
        neowboss = this;
        super.usePreBattleAction();
        //  AbstractDungeon.getCurrRoom().cannotLose = true;
        AbstractCharBoss.boss = null;
        // halfDead = true;
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_ENDING");


        int beatAmount = 2;
        if (AbstractDungeon.ascensionLevel >= 19) {
            beatAmount++;
        }

        int invincibleAmt = 300;
        if (AbstractDungeon.ascensionLevel >= 19) {
            invincibleAmt -= 100;
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new NeowInvulnerablePower(this, beatAmount)));

        for (int i = 0; i < 5; i++) {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new InvinciblePower(this, invincibleAmt), invincibleAmt));

        for (int i = 0; i < 5; i++) {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        }
        AbstractDungeon.actionManager.addToBottom(new NeowGainMinionPowersAction(this, 1));

        for (int i = 0; i < 5; i++) {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        }
        AbstractDungeon.actionManager.addToBottom(new NeowGainMinionPowersAction(this, 2));

        for (int i = 0; i < 5; i++) {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        }
        AbstractDungeon.actionManager.addToBottom(new NeowGainMinionPowersAction(this, 3));


    }


    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                playSfx();
                curses();

                if (hasPower(EnergyThief.POWER_ID)) {
                    getPower(EnergyThief.POWER_ID).onSpecificTrigger();
                }
                break;
            case 1:
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.6F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffectColored(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX + EYE1_X, this.hb.cY + EYE1_Y, Color.GOLD), 0.25F));

                if (hasPower(FeedingFrenzy.POWER_ID)) {
                    getPower(FeedingFrenzy.POWER_ID).flashWithoutSound();
                    AbstractDungeon.actionManager.addToBottom(new VampireDamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.FIRE, false, true));
                }
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.7F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffectColored(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX + EYE2_X, this.hb.cY + EYE2_Y, Color.GOLD), 0.25F));

                if (hasPower(FeedingFrenzy.POWER_ID)) {
                    getPower(FeedingFrenzy.POWER_ID).flashWithoutSound();
                    AbstractDungeon.actionManager.addToBottom(new VampireDamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.FIRE, false, true));
                }

                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.8F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffectColored(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX + EYE3_X, this.hb.cY + EYE3_Y, Color.GOLD), 0.25F));

                if (hasPower(FeedingFrenzy.POWER_ID)) {
                    getPower(FeedingFrenzy.POWER_ID).flashWithoutSound();
                    AbstractDungeon.actionManager.addToBottom(new VampireDamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.FIRE, false, true));
                }

                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                break;
            case 2:
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.YELLOW, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.GOLD, ShockWaveEffect.ShockWaveType.CHAOTIC), .5F));


                if (hasPower(FeedingFrenzy.POWER_ID)) {
                    getPower(FeedingFrenzy.POWER_ID).flashWithoutSound();
                    AbstractDungeon.actionManager.addToBottom(new VampireDamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(1), AbstractGameAction.AttackEffect.SMASH));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(1), AbstractGameAction.AttackEffect.SMASH));
                }

                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(downfallMod.getRandomDownfallCurse().makeStatEquivalentCopy(), 1, true, false, false, (float) Settings.WIDTH * 0.35F, (float) Settings.HEIGHT / 2.0F));
                //AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(downfallMod.getRandomDownfallCurse().makeStatEquivalentCopy(), 1, true, false, false, (float) Settings.WIDTH * 0.65F, (float) Settings.HEIGHT / 2.0F));

                if (hasPower(EnergyThief.POWER_ID)) {
                    getPower(EnergyThief.POWER_ID).onSpecificTrigger();
                }
                break;
            case 3:
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new InflameEffect(this), 0.25F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new InflameEffect(this), 0.25F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new InflameEffect(this), 0.25F));
                //   nukeDebuffs();
                //AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Shackled"));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.strAmt * 3), this.strAmt * 3));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this.blockAmt));

                if (hasPower(BlasphemersDemise.POWER_ID)) {
                    getPower(BlasphemersDemise.POWER_ID).onSpecificTrigger();
                }

                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playSfx() {

        int roll = MathUtils.random(3);

        if (roll == 0) {
            CardCrawlGame.sound.play("VO_NEOW_1A");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_NEOW_1B");
        } else if (roll == 2) {
            CardCrawlGame.sound.play("VO_NEOW_2A");
        } else {
            CardCrawlGame.sound.play("VO_NEOW_2B");
        }

    }


    protected void getMove(int num) {


        switch (turnNum) {
            case 0:
                this.setMove((byte) 0, Intent.STRONG_DEBUFF);
                this.turnNum++;
                break;
            case 1:
                this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base, 3, true);
                this.turnNum++;
                break;
            case 2:
                this.setMove((byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
                this.turnNum++;
                break;
            case 3:
                this.setMove((byte) 3, Intent.DEFEND_BUFF);
                this.turnNum = 1;
                break;
        }
    }

    @Override
    public void die() {
        //if (!this.hasPower(NeowInvulnerablePower.POWER_ID)) {
        super.die();
        useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        onBossVictoryLogic();
        onFinalBossVictoryLogic();
        CardCrawlGame.stopClock = true;
        // }
    }

    @Override
    public void dispose() {
        super.dispose();
        neowboss = null;
    }

    public boolean foggy() {
        return this.hasPower(SeeingDoubleProduct.POWER_ID);
    }

    @Override
    public void renderTip(SpriteBatch sb) {
        if (!foggy()) {
            super.renderTip(sb);
        }
    }


    @SpireOverride
    protected void renderName(SpriteBatch sb) {
        if (!foggy()) {
            SpireSuper.call(sb);
        }
    }

    @SpireOverride
    protected void renderPowerIcons(SpriteBatch sb, float x, float y) {
        if (!foggy() && !halfDead) {
            SpireSuper.call(sb, x, y);
        }
    }
}



