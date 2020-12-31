//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package downfall.events;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;
import downfall.relics.GremlinWheel;
import downfall.rooms.HeartShopRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;

import java.util.Objects;

public class GremlinWheelGame_Rest extends AbstractImageEvent {
    public static final String ID = "downfall:GremlinWheelGameRest";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String INTRO_DIALOG;
    private static final Logger logger = LogManager.getLogger(GremlinWheelGame_Rest.class.getName());
    private static final EventStrings eventStrings;
    private static final float START_Y;
    private static final float TARGET_Y;
    private static final int WHEEL_W = 1024;
    private static final int ARROW_W = 512;
    private static final float ARROW_OFFSET_X;
    private static final float A_2_HP_LOSS = 0.15F;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(GremlinWheelGame_Evil.ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO_DIALOG = DESCRIPTIONS[10];
        START_Y = Settings.OPTION_Y + 1000.0F * Settings.scale;
        TARGET_Y = Settings.OPTION_Y;
        ARROW_OFFSET_X = 300.0F * Settings.scale;
    }

    private GremlinWheelGame_Rest.CUR_SCREEN screen;
    private int result;
    private float resultAngle;
    private float tmpAngle;
    private boolean startSpin;
    private boolean finishSpin;
    private boolean doneSpinning;
    private boolean bounceIn;
    private float bounceTimer;
    private float animTimer;
    private float spinVelocity;
    private int goldAmount;
    private boolean purgeResult;
    private boolean buttonPressed;
    private Hitbox buttonHb;
    private Texture wheelImg;
    private Texture arrowImg;
    private Texture buttonImg;
    private float imgX;
    private float imgY;
    private float wheelAngle;
    private Color color;
    private float hpLossPercent;
    private boolean secondSpin;
    private boolean threatened;
    private boolean threatened2;
    private boolean goldchosen;

    public GremlinWheelGame_Rest() {
        super(NAME, INTRO_DIALOG, (downfallMod.assetPath("images/events/spinTheWheelFire.png")));
        this.screen = GremlinWheelGame_Rest.CUR_SCREEN.INTRO;
        this.startSpin = false;
        this.finishSpin = false;
        this.doneSpinning = false;
        this.bounceIn = true;
        this.bounceTimer = 1.0F;
        this.animTimer = 3.0F;
        this.spinVelocity = 200.0F;
        this.purgeResult = false;
        this.buttonPressed = false;
        this.buttonHb = new Hitbox(450.0F * Settings.scale, 300.0F * Settings.scale);
        this.imgX = (float) Settings.WIDTH / 2.0F;
        this.imgY = START_Y;
        this.wheelAngle = 0.0F;
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.hpLossPercent = 0.1F;
        this.wheelImg = ImageMaster.loadImage("images/events/wheel.png");
        this.arrowImg = ImageMaster.loadImage("images/events/wheelArrow.png");
        this.buttonImg = ImageMaster.loadImage("images/events/spinButton.png");
        this.noCardsInRewards = true;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.hpLossPercent = 0.15F;
        }

        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.setGold();
        this.hasDialog = true;
        this.hasFocus = true;
        this.buttonHb.move(500.0F * Settings.scale, -500.0F * Settings.scale);
    }

    private void setGold() {
        if (Objects.equals(AbstractDungeon.id, "Exordium")) {
            this.goldAmount = 100;
        } else if (Objects.equals(AbstractDungeon.id, "TheCity")) {
            this.goldAmount = 200;
        } else if (Objects.equals(AbstractDungeon.id, "TheBeyond")) {
            this.goldAmount = 300;
        }

    }

    public void update() {
        super.update();
        this.updatePosition();
        this.purgeLogic();
        if (this.bounceTimer == 0.0F && this.startSpin) {
            if (!this.buttonPressed) {
                this.buttonHb.cY = MathHelper.cardLerpSnap(this.buttonHb.cY, 50.0F * Settings.scale);
                this.buttonHb.move(this.buttonHb.cX, this.buttonHb.cY);
                this.buttonHb.update();
                if (this.buttonHb.hovered && InputHelper.justClickedLeft || CInputActionSet.proceed.isJustPressed()) {
                    this.buttonPressed = true;
                    this.buttonHb.hovered = false;
                    CardCrawlGame.sound.play("WHEEL");
                }
            } else {
                this.buttonHb.cY = MathHelper.cardLerpSnap(this.buttonHb.cY, -500.0F * Settings.scale);
            }
        }

        if (this.startSpin && this.bounceTimer == 0.0F && this.buttonPressed) {
            this.imgY = TARGET_Y;
            if (this.animTimer > 0.0F) {
                this.animTimer -= Gdx.graphics.getDeltaTime();
                this.wheelAngle += this.spinVelocity * Gdx.graphics.getDeltaTime();
            } else {
                this.finishSpin = true;
                this.animTimer = 3.0F;
                this.startSpin = false;
                this.tmpAngle = this.resultAngle;
            }
        } else if (this.finishSpin) {
            if (this.animTimer > 0.0F) {
                this.animTimer -= Gdx.graphics.getDeltaTime();
                if (this.animTimer < 0.0F) {
                    this.animTimer = 1.0F;
                    this.finishSpin = false;
                    this.doneSpinning = true;
                }

                this.wheelAngle = Interpolation.elasticIn.apply(this.tmpAngle, -180.0F, this.animTimer / 3.0F);
            }
        } else if (this.doneSpinning) {
            if (this.animTimer > 0.0F) {
                this.animTimer -= Gdx.graphics.getDeltaTime();
                if (this.animTimer <= 0.0F) {
                    this.bounceTimer = 1.0F;
                    this.bounceIn = false;
                }
            } else if (this.bounceTimer == 0.0F) {
                this.doneSpinning = false;
                this.imageEventText.clearAllDialogs();
                this.preApplyResult();
                GenericEventDialog.show();
            }
        }

        if (!GenericEventDialog.waitForInput) {
            this.buttonEffect(GenericEventDialog.getSelectedOption());
        }

    }

    private void updatePosition() {
        if (this.bounceTimer != 0.0F) {
            this.bounceTimer -= Gdx.graphics.getDeltaTime();
            if (this.bounceTimer < 0.0F) {
                this.bounceTimer = 0.0F;
            }

            if (this.bounceIn && this.startSpin) {
                this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.bounceTimer);
                this.imgY = Interpolation.bounceIn.apply(TARGET_Y, START_Y, this.bounceTimer);
            } else if (this.doneSpinning) {
                this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.bounceTimer);
                this.imgY = Interpolation.swingOut.apply(START_Y, TARGET_Y, this.bounceTimer);
            }
        }

    }

    private void preApplyResult() {
        switch (this.result) {
            case 0:
                this.imageEventText.updateBodyText(DESCRIPTIONS[11]);
                this.imageEventText.setDialogOption(OPTIONS[1]);
                this.goldchosen = true;
                break;
            case 1:
                this.imageEventText.updateBodyText(DESCRIPTIONS[11]);
                this.imageEventText.setDialogOption(OPTIONS[2]);
                break;
            case 2:
                this.imageEventText.updateBodyText(DESCRIPTIONS[11]);
                this.imageEventText.setDialogOption(OPTIONS[3]);
                break;
            case 3:
                this.imageEventText.updateBodyText(DESCRIPTIONS[11]);
                this.imageEventText.setDialogOption(OPTIONS[4]);
                break;
            case 4:
                this.imageEventText.updateBodyText(DESCRIPTIONS[11]);
                this.imageEventText.setDialogOption(OPTIONS[5]);
                break;
            default:
                this.imageEventText.updateBodyText(DESCRIPTIONS[11]);
                this.imageEventText.setDialogOption(OPTIONS[6] + (int) ((float) AbstractDungeon.player.maxHealth * this.hpLossPercent) + OPTIONS[7]);
                this.color = new Color(0.5F, 0.5F, 0.5F, 1.0F);
        }
        this.imageEventText.setDialogOption(OPTIONS[13]);


    }

    protected void buttonEffect(int buttonPressed) {
        //SlimeboundMod.logger.info(this.screen);

        switch (this.screen) {
            case INTRO:
                if (buttonPressed == 0) {
                    GenericEventDialog.hide();
                    this.result = AbstractDungeon.miscRng.random(0, 5);
                    this.resultAngle = (float) this.result * 60.0F + MathUtils.random(-10.0F, 10.0F);
                    this.wheelAngle = 0.0F;
                    this.startSpin = true;
                    this.bounceTimer = 2.0F;
                    this.animTimer = 2.0F;
                    this.spinVelocity = 1500.0F;
                    this.screen = CUR_SCREEN.POSTSPIN1;
                    break;
                }
            case POSTSPIN1:
                if (buttonPressed == 0) {
                    this.applyResult();
                    if (this.goldchosen) {
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldAmount));
                        AbstractDungeon.player.gainGold(this.goldAmount);
                    }
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(OPTIONS[8]);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[12]);
                    this.screen = CUR_SCREEN.LEAVE;
                    break;
                }
                if (buttonPressed == 1) {
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(OPTIONS[8]);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[13]);
                    this.screen = CUR_SCREEN.LEAVE;
                    if (AbstractDungeon.player.hasRelic(GremlinWheel.ID)){
                        GremlinWheel gw = (GremlinWheel)AbstractDungeon.player.getRelic(GremlinWheel.ID);
                        gw.justFailed = true;
                    }
                    break;
                }

            default:
                AbstractDungeon.getCurrRoom().clearEvent();
                AbstractRoom sRoom = new RestRoom();
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
                AbstractDungeon.getCurrRoom().smoked = false;
                AbstractDungeon.player.isEscaping = false;
                AbstractRoom.waitTimer = 0.1F;
                this.hasFocus = false;
                GenericEventDialog.hide();
                AbstractDungeon.currMapNode.setRoom(sRoom);
                AbstractDungeon.scene.nextRoom(sRoom);
                CardCrawlGame.fadeIn(1.5F);
                AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
                sRoom.onPlayerEntry();
                return;
        }

    }

    private void applyResult() {
        if (AbstractDungeon.player.hasRelic(GremlinWheel.ID)) {
            AbstractDungeon.player.getRelic(GremlinWheel.ID).setCounter(0);
        }
        switch (this.result) {
            case 0:
                this.hasFocus = false;
                logMetricGainGold("Wheel of Change", "Gold", this.goldAmount);
                break;
            case 1:
                AbstractDungeon.getCurrRoom().rewards.clear();
                AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                AbstractDungeon.getCurrRoom().addRelicToRewards(r);
                AbstractDungeon.combatRewardScreen.open();
                logMetric("Wheel of Change", "Relic");
                this.hasFocus = false;
                break;
            case 2:
                logMetricHeal("Wheel of Change", "Full Heal", AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
                AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                this.hasFocus = false;
                break;
            case 3:
                AbstractCard curse = new Decay();
                logMetricObtainCard("Wheel of Change", "Cursed", curse);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                this.hasFocus = false;
                break;
            case 4:
                if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                    AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[9], false, false, false, true);
                    this.roomEventText.hide();
                    this.purgeResult = true;
                }
                break;
            default:
                this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
                CardCrawlGame.sound.play("ATTACK_DAGGER_6");
                CardCrawlGame.sound.play("BLOOD_SPLAT");
                int damageAmount = (int) ((float) AbstractDungeon.player.maxHealth * this.hpLossPercent);
                AbstractDungeon.player.damage(new DamageInfo(null, damageAmount, DamageType.HP_LOSS));
                logMetricTakeDamage("Wheel of Change", "Damaged", damageAmount);
        }

    }

    private void purgeLogic() {
        if (this.purgeResult && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            logMetricCardRemoval("Wheel of Change", "Card Removal", c);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.effectList.add(new PurgeCardEffect(c));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.hasFocus = false;
            this.purgeResult = false;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.wheelImg, this.imgX - 512.0F, this.imgY - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, this.wheelAngle, 0, 0, 1024, 1024, false, false);
        sb.draw(this.arrowImg, this.imgX - 256.0F + ARROW_OFFSET_X + 180.0F * Settings.scale, this.imgY - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, false, false);
        if (this.buttonHb.hovered) {
            sb.draw(this.buttonImg, this.buttonHb.cX - 256.0F, this.buttonHb.cY - 256.0F + 100.0F * Settings.scale, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale * 1.05F, Settings.scale * 1.05F, 0.0F, 0, 0, 512, 512, false, false);
        } else {
            sb.draw(this.buttonImg, this.buttonHb.cX - 256.0F, this.buttonHb.cY - 256.0F + 100.0F * Settings.scale, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, false, false);
        }

        if (Settings.isControllerMode) {
            sb.draw(CInputActionSet.proceed.getKeyImg(), this.buttonHb.cX - 32.0F - 160.0F * Settings.scale, this.buttonHb.cY - 32.0F + 30.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }

        sb.setBlendFunction(770, 1);
        if (this.buttonHb.hovered) {
            sb.setColor(1.0F, 1.0F, 1.0F, 0.25F);
        } else {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, (MathUtils.cosDeg((float) (System.currentTimeMillis() / 5L % 360L)) + 1.25F) / 3.5F));
        }

        if (this.buttonHb.hovered) {
            sb.draw(this.buttonImg, this.buttonHb.cX - 256.0F, this.buttonHb.cY - 256.0F + 100.0F * Settings.scale, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale * 1.05F, Settings.scale * 1.05F, 0.0F, 0, 0, 512, 512, false, false);
        } else {
            sb.draw(this.buttonImg, this.buttonHb.cX - 256.0F, this.buttonHb.cY - 256.0F + 100.0F * Settings.scale, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, false, false);
        }

        sb.setBlendFunction(770, 771);
        this.buttonHb.render(sb);
    }

    public void dispose() {
        super.dispose();
        if (this.wheelImg != null) {
            this.wheelImg.dispose();
        }

        if (this.arrowImg != null) {
            this.arrowImg.dispose();
        }

        if (this.buttonImg != null) {
            this.buttonImg.dispose();
        }

    }

    private enum CUR_SCREEN {
        INTRO,
        LEAVE,
        POSTSPIN1,
        POSTSPIN2,
        FIGHT,
        COMPLETE;

        CUR_SCREEN() {
        }
    }
}
