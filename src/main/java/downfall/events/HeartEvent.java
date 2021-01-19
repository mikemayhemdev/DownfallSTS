package downfall.events;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.blights.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.helpers.TipTracker;
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
import downfall.downfallMod;
import downfall.util.HeartReward;
import downfall.vfx.CustomAnimatedNPC;
import downfall.vfx.TopLevelInfiniteSpeechBubble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

import static downfall.patches.EvilModeCharacterSelect.evilMode;

public class HeartEvent extends AbstractEvent {
    private static final Logger logger = LogManager.getLogger(HeartEvent.class.getName());
    private static final CharacterStrings characterStrings;
    public static final String[] NAMES;
    public static final String[] TEXT;
    public static final String[] OPTIONS;
    private CustomAnimatedNPC npc;
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

    private TopLevelInfiniteSpeechBubble speechBubble;

    public HeartEvent(boolean isDone) {
        dismissBubble(); //remove message from original event
        this.screenNum = 2;
        this.setPhaseToEvent = false;
        this.rewards = new ArrayList();
        this.pickCard = false;
        waitingToSave = false;
        if (this.npc == null) {
            this.npc = new CustomAnimatedNPC(1334.0F * Settings.scale, AbstractDungeon.floorY + 200.0F * Settings.scale, "images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", "idle", true, 0);
        }
        this.npc.portalRenderActive = true;
        this.npc.changeBorderColor(Color.MAROON);

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
            if (!(Boolean) TipTracker.tips.get("NEOW_INTRO")) {
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

        if (evilMode) {
            Settings.isFinalActAvailable = true;
            AbstractDungeon.topPanel.setPlayerName();
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

    public boolean hasPlayedRun(AbstractPlayer.PlayerClass p) {
        return UnlockTracker.getCurrentProgress(p) > 0 || UnlockTracker.getUnlockLevel(p) > 0;
    }

    public void update() {
        super.update();

        this.npc.update();

        Iterator var2;
        if (this.pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            CardGroup group = new CardGroup(CardGroupType.UNSPECIFIED);
            var2 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

            while (var2.hasNext()) {
                AbstractCard c = (AbstractCard) var2.next();
                group.addToBottom(c.makeCopy());
            }

            AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, TEXT[11]);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        for (HeartReward r : this.rewards) {
            r.update();
        }

        if (!this.setPhaseToEvent) {
            AbstractDungeon.getCurrRoom().phase = RoomPhase.EVENT;
            downfallMod.readyToDoThing = true;
            this.setPhaseToEvent = true;
        }

        if (!RoomEventDialog.waitForInput) {
            this.buttonEffect(this.roomEventText.getSelectedOption());
        }

        if (waitingToSave && !AbstractDungeon.isScreenUp && AbstractDungeon.topLevelEffects.isEmpty() && AbstractDungeon.player.relicsDoneAnimating()) {
            boolean doneAnims = true;
            var2 = AbstractDungeon.player.relics.iterator();

            while (var2.hasNext()) {
                AbstractRelic r = (AbstractRelic) var2.next();
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


    }

    private void talk(String msg) {
        if (speechBubble != null) speechBubble.dismiss();
        speechBubble = new TopLevelInfiniteSpeechBubble(DIALOG_X, DIALOG_Y, msg);
        AbstractDungeon.topLevelEffects.add(speechBubble);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
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
                    if (this.speechBubble != null) this.speechBubble.dismiss();
                    this.openMap();
                }
                break;
            case 3:
                this.dismissBubble();
                this.roomEventText.clearRemainingOptions();
                switch (buttonPressed) {
                    case 0:
                        ((HeartReward) this.rewards.get(0)).activate();
                        this.talk(TEXT[8]);
                        break;
                    case 1:
                        ((HeartReward) this.rewards.get(1)).activate();
                        this.talk(TEXT[8]);
                        break;
                    case 2:
                        ((HeartReward) this.rewards.get(2)).activate();
                        this.talk(TEXT[9]);
                        break;
                    case 3:
                        ((HeartReward) this.rewards.get(3)).activate();
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
                if (this.speechBubble != null) this.speechBubble.dismiss();
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
            AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new Spear());
        }

        if (AbstractDungeon.player.hasBlight("ToughEnemies")) {
            tmp = AbstractDungeon.player.getBlight("ToughEnemies");
            tmp.incrementUp();
            tmp.flash();
        } else {
            AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new Shield());
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
                        AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new GrotesqueTrophy());
                    }
                } else {
                    AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new Muzzle());
                }
            } else {
                AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new TimeMaze());
            }

        } else {
            AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new MimicInfestation());
        }
    }

    private void dailyBlessing() {
        rng = new Random(Settings.seed);
        this.dismissBubble();
        this.talk(TEXT[8]);
        if (ModHelper.isModEnabled("Heirloom")) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, AbstractDungeon.returnRandomRelic(RelicTier.RARE));
        }

        boolean addedCards = false;
        CardGroup group = new CardGroup(CardGroupType.UNSPECIFIED);
        AbstractCard card;
        if (ModHelper.isModEnabled("Allstar")) {
            addedCards = true;

            for (int i = 0; i < 5; ++i) {
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

                for (int i = 0; i < 5; ++i) {
                    AbstractCard tmpCard = rareCard.makeCopy();
                    AbstractDungeon.topLevelEffectsQueue.add(new FastCardObtainEffect(tmpCard, MathUtils.random((float) Settings.WIDTH * 0.2F, (float) Settings.WIDTH * 0.8F), MathUtils.random((float) Settings.HEIGHT * 0.3F, (float) Settings.HEIGHT * 0.7F)));
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

            for (int i = 0; i < 30; ++i) {
                card = AbstractDungeon.getCard(AbstractDungeon.rollRarity());
                if (!sealedGroup.contains(card)) {
                    sealedGroup.addToBottom(card.makeCopy());
                } else {
                    --i;
                }
            }

            for (AbstractCard c : sealedGroup.group) {
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
        this.roomEventText.removeDialogOption(0);
        if (hasPlayedRun(AbstractDungeon.player.chosenClass)) {
            this.roomEventText.addDialogOption(OPTIONS[5]);
        } else {
            this.roomEventText.addDialogOption(OPTIONS[6], true);
        }
        this.roomEventText.addDialogOption(((HeartReward) this.rewards.get(1)).optionLabel);
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
        this.roomEventText.updateDialogOption(0, ((HeartReward) this.rewards.get(0)).optionLabel);
        this.roomEventText.addDialogOption(((HeartReward) this.rewards.get(1)).optionLabel);
        this.roomEventText.addDialogOption(((HeartReward) this.rewards.get(2)).optionLabel);
        this.roomEventText.addDialogOption(((HeartReward) this.rewards.get(3)).optionLabel);
        this.screenNum = 3;
    }

    private void dismissBubble() {

        for (AbstractGameEffect e : AbstractDungeon.effectList) {
            if (e instanceof InfiniteSpeechBubble) {
                ((InfiniteSpeechBubble) e).dismiss();
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
        this.npc.render(sb);
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
        characterStrings = CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("Heart Event"));
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