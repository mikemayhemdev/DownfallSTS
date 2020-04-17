package evilWithin.events;


import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.blights.GrotesqueTrophy;
import com.megacrit.cardcrawl.blights.MimicInfestation;
import com.megacrit.cardcrawl.blights.Muzzle;
import com.megacrit.cardcrawl.blights.Shield;
import com.megacrit.cardcrawl.blights.Spear;
import com.megacrit.cardcrawl.blights.TimeMaze;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile.SaveType;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import com.megacrit.cardcrawl.vfx.InfiniteSpeechBubble;
import com.megacrit.cardcrawl.vfx.scene.LevelTransitionTextOverlayEffect;
import java.util.ArrayList;
import java.util.Iterator;

import evilWithin.util.HeartReward;
import evilWithin.vfx.PortalBorderEffect;
import evilWithin.vfx.TopLevelInfiniteSpeechBubble;
import org.apache.commons.net.pop3.POP3Reply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;

public class HeartEvent extends AbstractEvent {
    private static final Logger logger = LogManager.getLogger(HeartEvent.class.getName());
    private static final CharacterStrings characterStrings;
    public static final String[] NAMES;
    public static final String[] TEXT;
    public static final String[] OPTIONS;
    private AnimatedNpc npc;
    public static final String NAME;
    private int screenNum;
    private int bossCount;
    private boolean setPhaseToEvent;
    private ArrayList<HeartReward> rewards;
    public static Random rng;
    private boolean pickCard;
    public static boolean waitingToSave;
    private static final float DIALOG_X;
    private static final float DIALOG_Y;

    public static int borderEffectCount = 36;

    private ArrayList<PortalBorderEffect> borderEffects = new ArrayList<>();

    private float heartCenterX = 1334.0F * Settings.scale;
    private float heartCenterY = (AbstractDungeon.floorY - 60.0F * Settings.scale) + 300.0F * Settings.scale; //same values used in npc
    public Texture portalImage = ImageMaster.loadImage("evilWithinResources/images/vfx/beyondPortal.png");

    private HeartAnimListener animListener = new HeartAnimListener();

    // mask variables

    // I'm not sure what all these parameters are for, but the format, depth, and stencil all match the base game.
    // getWidth and getHeight make the frameBuffer match the player's game camera, which makes the FrameBuffer match the screen size.
    // it is possible to use a size other than the screen, but things will become warped if you don't take additional steps
    // like initializing a new camera and setting variables to use it. I prefer this simpler method.
    private FrameBuffer heartBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
    // I'm actually not sure why I end up needing multiple FrameBuffers for multiple mask operations, but it ended up
    // fixing my issues when I used multiple, so here we are.
    private FrameBuffer maskBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);

    private static final float PORTAL_GROW_TIME = 2.0f; //how long it takes the portal to grow to full size
    private float maskDuration = 0.0f;

    //I like the render options better when I'm using a TextureRegion.
    private static final TextureRegion MASK_REGION = new TextureRegion(new Texture("evilWithinResources/images/vfx/HeartMask.png"), 500, 500);

    public HeartEvent(boolean isDone) {
        dismissBubble(); //remove message from original event
        this.screenNum = 2;
        this.setPhaseToEvent = false;
        this.rewards = new ArrayList();
        this.pickCard = false;
        waitingToSave = false;
        if (this.npc == null) {
            this.npc = new AnimatedNpc(1350.0F * Settings.scale, AbstractDungeon.floorY - 30.0F * Settings.scale, "images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", "idle");
        }

        this.npc.skeleton.getRootBone().setScale(0.8F);


        npc.addListener(new HeartAnimListener());

        this.roomEventText.clear();
        this.playSfx();
        if (!Settings.isEndless || AbstractDungeon.floorNum <= 1) {
            if (Settings.isStandardRun() || Settings.isEndless && AbstractDungeon.floorNum <= 1) {
                this.bossCount = CardCrawlGame.playerPref.getInteger(AbstractDungeon.player.chosenClass.name() + "_SPIRITS", 0);
                AbstractDungeon.bossCount = this.bossCount;
            } else if (Settings.seedSet) {
                this.bossCount = 1;
            } else {
                this.bossCount = 0;
            }
        }

        this.body = "";
        if (Settings.isEndless && AbstractDungeon.floorNum > 1) {
            this.talk(TEXT[MathUtils.random(12, 14)]);
            this.screenNum = 999;
            this.roomEventText.addDialogOption(OPTIONS[0]);
        } else if (this.shouldSkipNeowDialog()) {
            this.screenNum = 10;
            this.talk(TEXT[10]);
            this.roomEventText.addDialogOption(OPTIONS[1]);
        } else if (!isDone) {
            if (!(Boolean)TipTracker.tips.get("NEOW_INTRO")) {
                this.screenNum = 0;
                TipTracker.neverShowAgain("NEOW_INTRO");
                this.talk(TEXT[0]);
                this.roomEventText.addDialogOption(OPTIONS[1]);
            } else {
                this.screenNum = 1;
                this.talk(TEXT[MathUtils.random(1, 3)]);
                this.roomEventText.addDialogOption(OPTIONS[1]);
            }

            AbstractDungeon.topLevelEffects.add(new LevelTransitionTextOverlayEffect(AbstractDungeon.name, AbstractDungeon.levelNum, true));
        } else {
            this.screenNum = 99;
            this.talk(TEXT[8]);
            this.roomEventText.addDialogOption(OPTIONS[3]);
        }

        this.hasDialog = true;
        this.hasFocus = true;

        for (int i = 1; i <= borderEffectCount; i++) {

            PortalBorderEffect effect = new PortalBorderEffect(heartCenterX, heartCenterY, i * 100 * (borderEffectCount / 360.0f));
            borderEffects.add(effect);
            effect.orbitalInterval = borderEffects.get(0).orbitalInterval;
            effect.calculateEllipseSize();
        }

    }

    public HeartEvent() {
        this(false);
    }

    private boolean shouldSkipNeowDialog() {
        if (Settings.seedSet && !Settings.isTrial && !Settings.isDailyRun) {
            return false;
        } else {
            return !Settings.isStandardRun();
        }
    }

    public void update() {
        super.update();

        for (PortalBorderEffect pb : borderEffects){
            pb.update();
        }

        Iterator var2;
        if (this.pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            CardGroup group = new CardGroup(CardGroupType.UNSPECIFIED);
            var2 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();
                group.addToBottom(c.makeCopy());
            }

            AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, TEXT[11]);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        Iterator var4 = this.rewards.iterator();

        while(var4.hasNext()) {
            HeartReward r = (HeartReward)var4.next();
            r.update();
        }

        if (!this.setPhaseToEvent) {
            AbstractDungeon.getCurrRoom().phase = RoomPhase.EVENT;
            this.setPhaseToEvent = true;
        }

        if (!RoomEventDialog.waitForInput) {
            this.buttonEffect(this.roomEventText.getSelectedOption());
        }

        if (waitingToSave && !AbstractDungeon.isScreenUp && AbstractDungeon.topLevelEffects.isEmpty() && AbstractDungeon.player.relicsDoneAnimating()) {
            boolean doneAnims = true;
            var2 = AbstractDungeon.player.relics.iterator();

            while(var2.hasNext()) {
                AbstractRelic r = (AbstractRelic)var2.next();
                if (!r.isDone) {
                    doneAnims = false;
                    break;
                }
            }

            if (doneAnims) {
                waitingToSave = false;
                SaveHelper.saveIfAppropriate(SaveType.POST_NEOW);
            }
        }


        //mask effect
        if (maskDuration < PORTAL_GROW_TIME) {
            maskDuration += Gdx.graphics.getDeltaTime();

            for (PortalBorderEffect pb : borderEffects){
                pb.ELLIPSIS_SCALE = maskDuration / PORTAL_GROW_TIME;
                pb.calculateEllipseSize();
            }

            if (maskDuration > PORTAL_GROW_TIME) {
                maskDuration = PORTAL_GROW_TIME;
                for (PortalBorderEffect pb : borderEffects){
                    pb.ELLIPSIS_SCALE = 1F;
                    pb.calculateEllipseSize();
                }
            }
        }
    }

    private void talk(String msg) {
        AbstractDungeon.topLevelEffects.add(new TopLevelInfiniteSpeechBubble(DIALOG_X, DIALOG_Y, msg));
    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screenNum) {
            case 0:
                this.dismissBubble();
                this.talk(TEXT[4]);
                if (this.bossCount == 0 && !Settings.isTestingNeow) {
                    this.miniBlessing();
                } else {
                    this.blessing();
                }
                break;
            case 1:
                this.dismissBubble();
                logger.info(this.bossCount);
                if (this.bossCount == 0 && !Settings.isTestingNeow) {
                    this.miniBlessing();
                } else {
                    this.blessing();
                }
                break;
            case 2:
                if (buttonPressed == 0) {
                    this.blessing();
                } else {
                    this.openMap();
                }
                break;
            case 3:
                this.dismissBubble();
                this.roomEventText.clearRemainingOptions();
                switch(buttonPressed) {
                    case 0:
                        ((HeartReward)this.rewards.get(0)).activate();
                        this.talk(TEXT[8]);
                        break;
                    case 1:
                        ((HeartReward)this.rewards.get(1)).activate();
                        this.talk(TEXT[8]);
                        break;
                    case 2:
                        ((HeartReward)this.rewards.get(2)).activate();
                        this.talk(TEXT[9]);
                        break;
                    case 3:
                        ((HeartReward)this.rewards.get(3)).activate();
                        this.talk(TEXT[9]);
                }

                this.screenNum = 99;
                this.roomEventText.updateDialogOption(0, OPTIONS[3]);
                this.roomEventText.clearRemainingOptions();
                waitingToSave = true;
                break;
            case 10:
                this.dailyBlessing();
                this.roomEventText.clearRemainingOptions();
                this.roomEventText.updateDialogOption(0, OPTIONS[3]);
                this.screenNum = 99;
                break;
            case 999:
                this.endlessBlight();
                this.roomEventText.clearRemainingOptions();
                this.roomEventText.updateDialogOption(0, OPTIONS[3]);
                this.screenNum = 99;
                break;
            default:
                this.openMap();
        }

    }

    private void endlessBlight() {
        AbstractBlight tmp;
        if (AbstractDungeon.player.hasBlight("DeadlyEnemies")) {
            tmp = AbstractDungeon.player.getBlight("DeadlyEnemies");
            tmp.incrementUp();
            tmp.flash();
        } else {
            AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, new Spear());
        }

        if (AbstractDungeon.player.hasBlight("ToughEnemies")) {
            tmp = AbstractDungeon.player.getBlight("ToughEnemies");
            tmp.incrementUp();
            tmp.flash();
        } else {
            AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, new Shield());
        }

        this.uniqueBlight();
    }

    private void uniqueBlight() {
        AbstractBlight temp = AbstractDungeon.player.getBlight("MimicInfestation");
        if (temp != null) {
            temp = AbstractDungeon.player.getBlight("TimeMaze");
            if (temp != null) {
                temp = AbstractDungeon.player.getBlight("FullBelly");
                if (temp != null) {
                    temp = AbstractDungeon.player.getBlight("GrotesqueTrophy");
                    if (temp != null) {
                        AbstractDungeon.player.getBlight("GrotesqueTrophy").stack();
                    } else {
                        AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, new GrotesqueTrophy());
                    }
                } else {
                    AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, new Muzzle());
                }
            } else {
                AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, new TimeMaze());
            }

        } else {
            AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, new MimicInfestation());
        }
    }

    private void dailyBlessing() {
        rng = new Random(Settings.seed);
        this.dismissBubble();
        this.talk(TEXT[8]);
        if (ModHelper.isModEnabled("Heirloom")) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, AbstractDungeon.returnRandomRelic(RelicTier.RARE));
        }

        boolean addedCards = false;
        CardGroup group = new CardGroup(CardGroupType.UNSPECIFIED);
        AbstractCard card;
        if (ModHelper.isModEnabled("Allstar")) {
            addedCards = true;

            for(int i = 0; i < 5; ++i) {
                card = AbstractDungeon.getColorlessCardFromPool(AbstractDungeon.rollRareOrUncommon(0.5F));
                UnlockTracker.markCardAsSeen(card.cardID);
                group.addToBottom(card.makeCopy());
            }
        }

        if (ModHelper.isModEnabled("Specialized")) {
            AbstractCard rareCard;
            if (!ModHelper.isModEnabled("SealedDeck") && !ModHelper.isModEnabled("Draft")) {
                addedCards = true;
                rareCard = AbstractDungeon.returnTrulyRandomCard();
                UnlockTracker.markCardAsSeen(rareCard.cardID);
                group.addToBottom(rareCard.makeCopy());
                group.addToBottom(rareCard.makeCopy());
                group.addToBottom(rareCard.makeCopy());
                group.addToBottom(rareCard.makeCopy());
                group.addToBottom(rareCard.makeCopy());
            } else {
                rareCard = AbstractDungeon.returnTrulyRandomCard();

                for(int i = 0; i < 5; ++i) {
                    AbstractCard tmpCard = rareCard.makeCopy();
                    AbstractDungeon.topLevelEffectsQueue.add(new FastCardObtainEffect(tmpCard, MathUtils.random((float)Settings.WIDTH * 0.2F, (float)Settings.WIDTH * 0.8F), MathUtils.random((float)Settings.HEIGHT * 0.3F, (float)Settings.HEIGHT * 0.7F)));
                }
            }
        }

        if (addedCards) {
            AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, TEXT[11]);
        }

        if (ModHelper.isModEnabled("Draft")) {
            AbstractDungeon.cardRewardScreen.draftOpen();
        }

        this.pickCard = true;
        if (ModHelper.isModEnabled("SealedDeck")) {
            CardGroup sealedGroup = new CardGroup(CardGroupType.UNSPECIFIED);

            for(int i = 0; i < 30; ++i) {
                card = AbstractDungeon.getCard(AbstractDungeon.rollRarity());
                if (!sealedGroup.contains(card)) {
                    sealedGroup.addToBottom(card.makeCopy());
                } else {
                    --i;
                }
            }

            Iterator var11 = sealedGroup.group.iterator();

            while(var11.hasNext()) {
                AbstractCard c = (AbstractCard)var11.next();
                UnlockTracker.markCardAsSeen(c.cardID);
            }

            AbstractDungeon.gridSelectScreen.open(sealedGroup, 10, OPTIONS[4], false);
        }

        this.roomEventText.clearRemainingOptions();
        this.screenNum = 99;
    }

    private void miniBlessing() {
        AbstractDungeon.bossCount = 0;
        this.dismissBubble();
        this.talk(TEXT[MathUtils.random(4, 6)]);
        this.rewards.add(new HeartReward(true));
        this.rewards.add(new HeartReward(false));
        this.roomEventText.clearRemainingOptions();
        this.roomEventText.updateDialogOption(0, OPTIONS[5]);
        this.roomEventText.addDialogOption(((HeartReward)this.rewards.get(1)).optionLabel);
        this.screenNum = 3;
    }

    private void blessing() {
        logger.info("BLESSING");
        rng = new Random(Settings.seed);
        logger.info("COUNTER: " + rng.counter);
        AbstractDungeon.bossCount = 0;
        this.dismissBubble();
        this.talk(TEXT[7]);
        this.rewards.add(new HeartReward(0));
        this.rewards.add(new HeartReward(1));
        this.rewards.add(new HeartReward(2));
        this.rewards.add(new HeartReward(3));
        this.roomEventText.clearRemainingOptions();
        this.roomEventText.updateDialogOption(0, ((HeartReward)this.rewards.get(0)).optionLabel);
        this.roomEventText.addDialogOption(((HeartReward)this.rewards.get(1)).optionLabel);
        this.roomEventText.addDialogOption(((HeartReward)this.rewards.get(2)).optionLabel);
        this.roomEventText.addDialogOption(((HeartReward)this.rewards.get(3)).optionLabel);
        this.screenNum = 3;
    }

    private void dismissBubble() {
        Iterator var1 = AbstractDungeon.effectList.iterator();

        while(var1.hasNext()) {
            AbstractGameEffect e = (AbstractGameEffect)var1.next();
            if (e instanceof TopLevelInfiniteSpeechBubble) {
                ((TopLevelInfiniteSpeechBubble)e).dismiss();
            }
        }

    }

    private void playSfx() {
        CardCrawlGame.sound.play("HEART_SIMPLE");
    }

    public void logMetric(String actionTaken) {
        AbstractEvent.logMetric(NAME, actionTaken);
    }

    public void render(SpriteBatch sb) {



        /*
        Masking is done by instructing the SpriteBatch, via blend function, to only keep certain pixels based upon a
        "mask" texture rendered after the thing you want masked is rendered. In a vacuum, all you have to do is set the
        blend function as appropriate, then render the mask. However, when other game elements are being rendered, the
        mask will end up discarding pixels in the background, leading to undesired effects like black boxes.
        To avoid this, we render the things that we want the mask applied to inside of a FrameBuffer, then capture the
        contents of the FrameBuffer as a texture, and then finally render that texture in the regular scene.
        */

        // Since masking only works with a large enough texture and we're going to be shrinking the mask to start, we
        // need to first assemble a mask large enough to cover the unwanted parts of the render, using an additional
        // FrameBuffer.

        // A FrameBuffer is slightly expensive, so we don't want to be initializing one every frame. It's a private variable.
        // To properly use a frame buffer, you must start it while the sprite batch is not running, then start the sprite batch again.

        sb.end();
        maskBuffer.begin();

        // since the FrameBuffer is an entirely new rendering environment and we're using it between frames, some values
        // need to be set and re-set each time
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glColorMask(true,true,true,true);

        sb.begin();
        sb.setColor(Color.WHITE); //to be honest I don't know why we need to set the color again, but none of it works without it.

        /*
         Since a texture captured by a FrameBuffer is the size of the FrameBuffer and our FrameBuffer is the size of the screen,
         the transparency pixels will exist at any size we need by default, and we only need to render a portal mask, scaled to the
         effect duration. (I've put duration management stuff in update()). Since we're not actually yet using it as a mask,
         we're effectively just rendering a black circle for now, so we don't need to use the blend functions yet.

         the render function looks complicated, but it's quite simple. Since rendering starts at the bottom left of the texture,
         we want to find the center of the targeted mid-point, and then subtract half of the width and height of the
         texture from the X and Y coordinates to find the render point. Then, since we're applying a transform, we need to
         declare a center point of the texture, which is the center. This is relative to the X and Y coordinates we passed before,
         so we'll just send half width and height again. The next coordinates should be the width and height of the texture,
         then, apply any relevant transforms. In this case, scale for both width and height. The other transform is rotation,
         but we aren't using it in this case.
        */

        //we'll use a simple scale variable based on the duration. At the start, it's super tiny. At the end, it should equal 1.0.
        float scale = (maskDuration / PORTAL_GROW_TIME) * Settings.scale;


        float w = MASK_REGION.getRegionWidth();
        float h = MASK_REGION.getRegionHeight();
        sb.draw(MASK_REGION, heartCenterX - w / 2, heartCenterY - h / 2, w / 2, h / 2, w, h, scale, scale, 0.0f);

        // now that the mask is rendered inside the frameBuffer, we want to end the frameBuffer, and capture the texture.
        // Before a frame buffer is ended, the sprite batch must end, or weird operations will happen with the GPU.

        sb.end();
        maskBuffer.end();

        Texture tmpTexture = maskBuffer.getColorBufferTexture(); //this captures the contents of the FrameBuffer as a texture
        TextureRegion tmpMask = new TextureRegion(tmpTexture); //again, I prefer the options provided by TextureRegion class

        // Due to a quirk in how StS runs the libGdx engine, and I don't know the details of this myself, FrameBuffers
        // end up rendering upside-down. This fixes it.
        tmpMask.flip(false, true);

        // now we want to start the second frame buffer and render the npc inside of it, followed by the mask.
        // again, the FrameBuffer has to be started before the SpriteBatch.
        heartBuffer.begin();
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glColorMask(true,true,true,true);
        sb.begin();

        sb.setColor(Color.WHITE);

        // render the npc. Since we're using a FrameBuffer that's the same size as the screen, there should be no issues
        // using the regular render function of the npc. Note that if you want any background, you'd render it right here
        // right before this render.

        sb.draw(this.portalImage, this.heartCenterX - 250F, this.heartCenterY - 250F);
        this.npc.render(sb);

        /*
        Now we have the heart rendered inside the frame buffer, and we want to remove any unwanted pixels outside the mask.
        to do so, we need to use blend functions.
        this one discards pixels that are covered by the non-transparent pixels in the mask:
        sb.setBlendFunction(GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_ALPHA);
        but since we're using the other kind of mask, we want it to discard pixels covered by transparency:
         */
        sb.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_ALPHA);

        // then, we render the mask.
        // since the Framebuffer is the size of the screen, the texture is also therefore the size of the screen. That makes
        // this mask really easy to render.
        sb.draw(tmpMask, 0, 0);

        // IMPORTANT! when you set a blend function, always remember to reset it to StS's default after you're done with it:
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // again, we end the sprite batch and the frameBuffer, capture the texture, and flip it.
        sb.end();
        heartBuffer.end();
        TextureRegion maskedHeart = new TextureRegion(heartBuffer.getColorBufferTexture());
        maskedHeart.flip(false, true);

        // then, finally, we restart the SpriteBatch and render our final texture.
        sb.begin();
        sb.draw(maskedHeart, 0, 0);

        // now you can render whatever you like overtop it.
    }

    public void dispose() {
        super.dispose();
        if (this.npc != null) {
            logger.info("Disposing Neow asset.");
            this.npc.dispose();
            this.npc = null;
        }

    }

    static {
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Heart Event");
        NAMES = characterStrings.NAMES;
        TEXT = characterStrings.TEXT;
        OPTIONS = characterStrings.OPTIONS;
        NAME = NAMES[0];
        rng = null;
        waitingToSave = false;
        DIALOG_X = 1200F * Settings.scale;
        DIALOG_Y = 500F * Settings.scale;
    }
}