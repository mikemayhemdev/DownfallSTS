package downfall.util;

import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue.Save;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import downfall.downfallMod;
import downfall.vfx.CustomAnimatedNPC;
import downfall.vfx.TopLevelSpeechBubble;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class HeartMerchant implements Disposable {
    private static final CharacterStrings characterStrings;
    public static final String[] NAMES;
    public static final String[] TEXT;
    public static final String[] ENDING_TEXT;
    public CustomAnimatedNPC anim;
    public static final float DRAW_X;
    public static final float DRAW_Y;
    public Hitbox hb;
    private ArrayList<AbstractCard> cards1;
    private ArrayList<AbstractCard> cards2;
    private ArrayList<String> idleMessages;
    private float speechTimer;
    private boolean saidWelcome;
    private int shopScreen;
    protected float modX;
    protected float modY;

    public HeartMerchant() {
        this(0.0F, 0.0F, 1);
    }

    public HeartMerchant(float x, float y, int newShopScreen) {
       // //SlimeboundMod.logger.info("New Heart Merchant made");
        this.cards1 = new ArrayList();
        this.cards2 = new ArrayList();
        this.idleMessages = new ArrayList();
        this.speechTimer = 1.5F;
        this.saidWelcome = false;
        this.shopScreen = 1;
        this.anim = new CustomAnimatedNPC(DRAW_X, DRAW_Y, "images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", "idle", true,0);
        AbstractCard c;
        for(c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.ATTACK, true).makeCopy(); c.color == AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.ATTACK, true).makeCopy()) {
        }

        this.cards1.add(c);

        for(c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.ATTACK, true).makeCopy(); Objects.equals(c.cardID, ((AbstractCard)this.cards1.get(this.cards1.size() - 1)).cardID) || c.color == AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.ATTACK, true).makeCopy()) {
        }

        this.cards1.add(c);

        for(c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true).makeCopy(); c.color == AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true).makeCopy()) {
        }

        this.cards1.add(c);

        for(c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true).makeCopy(); Objects.equals(c.cardID, ((AbstractCard)this.cards1.get(this.cards1.size() - 1)).cardID) || c.color == AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true).makeCopy()) {
        }

        this.cards1.add(c);

        for(c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.POWER, true).makeCopy(); c.color == AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.POWER, true).makeCopy()) {
        }

        this.cards1.add(c);
        this.cards2.add(AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.UNCOMMON).makeCopy());
        this.cards2.add(AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE).makeCopy());
        if (AbstractDungeon.id.equals("TheEnding")) {
            Collections.addAll(this.idleMessages, ENDING_TEXT);
        } else {
            Collections.addAll(this.idleMessages, TEXT);
        }

        this.speechTimer = 1.5F;
        this.modX = x;
        this.modY = y;
        this.shopScreen = newShopScreen;
        AbstractDungeon.shopScreen.init(this.cards1, this.cards2);
    }

    public void update() {


        this.hb.update();
        this.anim.update();


        if (this.hb.hovered){
            this.anim.changeBorderColor(Color.WHITE);
            this.anim.highlighted = true;
        } else {
            this.anim.changeBorderColor(Color.VIOLET);
            this.anim.highlighted = false;
        }

        if ((this.hb.hovered && InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) && !AbstractDungeon.isScreenUp && !AbstractDungeon.isFadingOut && !AbstractDungeon.player.viewingRelics) {
            AbstractDungeon.overlayMenu.proceedButton.setLabel(NAMES[0]);
            this.saidWelcome = true;
            AbstractDungeon.getCurrRoom().rewards.clear();
            AbstractDungeon.getCurrRoom().rewardAllowed = false;
            AbstractDungeon.shopScreen.open();
            this.hb.hovered = false;
        }



        this.speechTimer -= Gdx.graphics.getDeltaTime();
        if (this.speechTimer < 0.0F && this.shopScreen == 1) {
            String msg = (String)this.idleMessages.get(MathUtils.random(0, this.idleMessages.size() - 1));
            if (!this.saidWelcome) {
                this.saidWelcome = true;
                this.welcomeSfx();
                msg = NAMES[1];
            } else {
                this.playMiscSfx();
            }

            if (MathUtils.randomBoolean()) {
                AbstractDungeon.effectList.add(new TopLevelSpeechBubble(this.hb.cX - 50.0F * Settings.scale, this.hb.cY + 70.0F * Settings.scale, 3.0F, msg, false));
            } else {
                AbstractDungeon.effectList.add(new TopLevelSpeechBubble(this.hb.cX + 50.0F * Settings.scale, this.hb.cY + 70.0F * Settings.scale, 3.0F, msg, true));
            }

            this.speechTimer = MathUtils.random(40.0F, 60.0F);
        }



    }

    private void welcomeSfx() {
        CardCrawlGame.sound.play("HEART_SIMPLE");
    }

    private void playMiscSfx() {
        CardCrawlGame.sound.play("HEART_SIMPLE");
    }

    public void render(SpriteBatch sb) {

        if (Settings.isControllerMode) {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.select.getKeyImg(), DRAW_X - 32.0F + 150.0F * Settings.scale, DRAW_Y - 32.0F + 100.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }

        if (this.hb != null) {
            this.anim.render(sb);
            this.hb.render(sb);
        }

        ////SlimeboundMod.logger.info("Heart Merchant render tick.");
    }

    public void dispose() {
        if (this.anim != null) {
            this.anim.dispose();
           // //SlimeboundMod.logger.info("Heart Merchant disposed.");
        }

    }

    public void spawnHitbox(){
        this.hb = new Hitbox(500.0F * Settings.scale, 700.0F * Settings.scale);
        this.hb.move(DRAW_X , DRAW_Y);
        this.anim.portalRenderActive = true;
    }

    static {
        characterStrings = CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("HeartMerchant"));
        NAMES = characterStrings.NAMES;
        TEXT = characterStrings.TEXT;
        ENDING_TEXT = characterStrings.OPTIONS;
        DRAW_X = 1240.0F * Settings.scale;
        DRAW_Y = AbstractDungeon.floorY + 220.0F * Settings.scale;
    }
}
