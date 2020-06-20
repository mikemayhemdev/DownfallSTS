package downfall.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.powers.TotemInvulnerablePower;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;


public class AbstractTotemMonster extends AbstractMonster {

    public static Float beamOffsetX = 25F * Settings.scale;
    public static Float beamOffsetY = 10F * Settings.scale;
    public static Float beamOffsetX2 = -35F * Settings.scale;
    public static Float beamOffsetY2 = 10F * Settings.scale;
    public Integer baseHP = 50;
    public Integer HPAscBuffed = 10;
    public Intent intentType = Intent.BUFF;
    public Color totemLivingColor;
    private Method refrenderIntentVfxBehind;
    private Method refrenderIntent;
    private Method refrenderIntentVfxAfter;
    private Method refrenderDamageRange;
    private Method refrenderName;
    private Method refrenderHealthBg;
    private Method refrenderOrangeHealthBar;
    private Method refrenderGreenHealthBar;
    private Method refrenderHealthText;
    private Method refrenderBlockIconAndValue;
    private Method refrenderPowerIcons;
    private Method refrenderRedHealthBar;
    private Method refrenderBlockOutline;
    private Method refupdateDeathAnimation;
    private Method refupdateIntent;
    private boolean wasFalling = false;
    private boolean spawnedAfterFirst3 = false;


    public AbstractTotemMonster(String name, String ID, String imgPath) {
        super(name, ID, 420, 0.0F, 0F, 160.0F, 180.0F, null, -50.0F, 15.0F);

        //ReflectionHacks.setPrivate(this, AbstractCreature.class,"HB_Y_OFFSET_DIST",-200F);

        try {
            refrenderIntentVfxBehind = AbstractMonster.class.getDeclaredMethod("renderIntentVfxBehind", SpriteBatch.class);
            refrenderIntent = AbstractMonster.class.getDeclaredMethod("renderIntent", SpriteBatch.class);
            refrenderIntentVfxAfter = AbstractMonster.class.getDeclaredMethod("renderIntentVfxAfter", SpriteBatch.class);
            refrenderDamageRange = AbstractMonster.class.getDeclaredMethod("renderDamageRange", SpriteBatch.class);
            refrenderName = AbstractMonster.class.getDeclaredMethod("renderName", SpriteBatch.class);
            refrenderHealthBg = AbstractCreature.class.getDeclaredMethod("renderHealthBg", SpriteBatch.class, float.class, float.class);
            refrenderOrangeHealthBar = AbstractCreature.class.getDeclaredMethod("renderOrangeHealthBar", SpriteBatch.class, float.class, float.class);
            refrenderGreenHealthBar = AbstractCreature.class.getDeclaredMethod("renderGreenHealthBar", SpriteBatch.class, float.class, float.class);
            refrenderHealthText = AbstractCreature.class.getDeclaredMethod("renderHealthText", SpriteBatch.class, float.class);
            refrenderBlockIconAndValue = AbstractCreature.class.getDeclaredMethod("renderBlockIconAndValue", SpriteBatch.class, float.class, float.class);
            refrenderPowerIcons = AbstractCreature.class.getDeclaredMethod("renderPowerIcons", SpriteBatch.class, float.class, float.class);
            refrenderRedHealthBar = AbstractCreature.class.getDeclaredMethod("renderRedHealthBar", SpriteBatch.class, float.class, float.class);
            refrenderBlockOutline = AbstractCreature.class.getDeclaredMethod("renderBlockOutline", SpriteBatch.class, float.class, float.class);
            refupdateDeathAnimation = AbstractMonster.class.getDeclaredMethod("updateDeathAnimation");
            refupdateIntent = AbstractMonster.class.getDeclaredMethod("updateIntent");


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        this.refrenderIntentVfxBehind.setAccessible(true);
        this.refrenderIntent.setAccessible(true);
        this.refrenderIntentVfxAfter.setAccessible(true);
        this.refrenderDamageRange.setAccessible(true);
        this.refrenderName.setAccessible(true);
        this.refrenderHealthBg.setAccessible(true);
        this.refrenderOrangeHealthBar.setAccessible(true);
        this.refrenderGreenHealthBar.setAccessible(true);
        this.refrenderHealthText.setAccessible(true);
        this.refrenderBlockIconAndValue.setAccessible(true);
        this.refrenderPowerIcons.setAccessible(true);
        this.refrenderRedHealthBar.setAccessible(true);
        this.refrenderBlockOutline.setAccessible(true);
        this.refupdateDeathAnimation.setAccessible(true);
        this.refupdateIntent.setAccessible(true);


        this.type = EnemyType.ELITE;
        this.dialogX = -100.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        this.drawY = AbstractDungeon.floorY - 50F * Settings.scale;
        this.drawX = Settings.WIDTH * 0.75F;

        this.hb.move(this.hb.cX, this.hb.cY - 90F * Settings.scale);


        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(baseHP + HPAscBuffed);
        } else {
            this.setHp(baseHP);
        }

        if (spawnedAfterFirst3) {
            this.setMove((byte) 0, Intent.STUN);
            createIntent();
        }

    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new TotemInvulnerablePower(this)));

    }

    public void totemAttack() {

    }


    public void takeTurn() {
        totemAttack();

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }


    public void update() {

        Iterator var1 = this.powers.iterator();

        while (var1.hasNext()) {
            AbstractPower p = (AbstractPower) var1.next();
            p.updateParticles();
        }

        this.updateReticle();
        this.updateHealthBar();
        this.updateAnimations();
        try {
            refupdateDeathAnimation.invoke(this);
            this.intentHb.move(this.hb.cX - 120F * Settings.scale, this.drawY + 160F * Settings.scale);

            refupdateIntent.invoke(this);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        this.tint.update();
    }


    protected void getMove(int num) {
        getUniqueTotemMove();
    }

    public void getUniqueTotemMove() {

    }

    @Override
    public void render(SpriteBatch sb) {

        if (!this.isDead && !this.escaped) {
            if (this.atlas == null) {
                sb.setColor(this.tint.color);
                if (this.img != null) {
                    sb.draw(this.img, this.drawX - (float) this.img.getWidth() * Settings.scale / 2.0F + this.animX, this.drawY + this.animY + AbstractDungeon.sceneOffsetY, (float) this.img.getWidth() * Settings.scale, (float) this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipHorizontal, this.flipVertical);
                }
            } else {
                this.state.update(Gdx.graphics.getDeltaTime());
                this.state.apply(this.skeleton);
                this.skeleton.updateWorldTransform();
                this.skeleton.setPosition(this.drawX + this.animX, this.drawY + this.animY + AbstractDungeon.sceneOffsetY);
                this.skeleton.setColor(this.tint.color);
                this.skeleton.setFlip(this.flipHorizontal, this.flipVertical);
                sb.end();
                CardCrawlGame.psb.begin();
                sr.draw(CardCrawlGame.psb, this.skeleton);
                CardCrawlGame.psb.end();
                sb.begin();
                sb.setBlendFunction(770, 771);
            }

            if (this == AbstractDungeon.getCurrRoom().monsters.hoveredMonster && this.atlas == null) {
                sb.setBlendFunction(770, 1);
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));
                if (this.img != null) {
                    sb.draw(this.img, this.drawX - (float) this.img.getWidth() * Settings.scale / 2.0F + this.animX, this.drawY + this.animY + AbstractDungeon.sceneOffsetY, (float) this.img.getWidth() * Settings.scale, (float) this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipHorizontal, this.flipVertical);
                    sb.setBlendFunction(770, 771);
                }
            }

            if (!this.isDying && !this.isEscaping && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.player.isDead && !AbstractDungeon.player.hasRelic("Runic Dome") && this.intent != AbstractMonster.Intent.NONE && !Settings.hideCombatElements) {
                try {

                    //this.intentHb.move(this.hb.cX,this.drawY + 180F * Settings.scale);

                    refrenderIntentVfxBehind.invoke(this, sb);

                    refrenderIntent.invoke(this, sb);

                    refrenderIntentVfxAfter.invoke(this, sb);

                    refrenderDamageRange.invoke(this, sb);

                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }


            }


            this.intentHb.render(sb);
            this.healthHb.move(this.hb.cX, this.drawY + 50F * Settings.scale);

            this.healthHb.render(sb);
        }

        if (!AbstractDungeon.player.isDead) {
            this.renderHealth(sb);
            try {
                refrenderName.invoke(this, sb);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        this.hb.render(sb);

    }



}