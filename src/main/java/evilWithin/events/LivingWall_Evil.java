//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package evilWithin.events;


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
import com.megacrit.cardcrawl.core.AbstractCreature;
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
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.monsters.exordium.GremlinThief;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import evilWithin.relics.GremlinWheel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;

import java.util.Objects;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class LivingWall_Evil extends AbstractImageEvent {
    public static final String ID = "Living Wall";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String DIALOG_1;
    private static final String RESULT_DIALOG;
    private LivingWall_Evil.CurScreen screen;
    private boolean pickCard;
    private LivingWall_Evil.Choice choice;

    public LivingWall_Evil() {
        super(NAME, DIALOG_1, "images/events/livingWall.jpg");
        this.screen = LivingWall_Evil.CurScreen.INTRO;
        this.pickCard = false;
        this.choice = null;
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
        if (AbstractDungeon.player.masterDeck.hasUpgradableCards()) {
            this.imageEventText.setDialogOption(OPTIONS[2]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[7], true);
        }

    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_LIVING_WALL");
        }

    }

    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            switch(this.choice) {
                case FORGET:
                    CardCrawlGame.sound.play("CARD_EXHAUST");
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0), (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                    AbstractEvent.logMetricCardRemoval("Living Wall", "Forget", (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                    AbstractDungeon.player.masterDeck.removeCard((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                    break;
                case CHANGE:
                    AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    AbstractDungeon.player.masterDeck.removeCard(c);
                    AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);
                    AbstractCard transCard = AbstractDungeon.getTransformedCard();
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(transCard, c.current_x, c.current_y));
                    AbstractEvent.logMetricTransformCard("Living Wall", "Change", c, transCard);
                    break;
                case GROW:
                    AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0)).upgrade();
                    AbstractCard upgCard = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    AbstractEvent.logMetricCardUpgrade("Living Wall", "Grow", upgCard);
                    AbstractDungeon.player.bottledCardUpgradeCheck(upgCard);
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.pickCard = false;
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
            case INTRO:
                switch(buttonPressed) {
                    case 0:
                        this.choice = LivingWall_Evil.Choice.FORGET;
                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[3], false, false, false, true);
                        }
                        break;
                    case 1:
                        this.choice = LivingWall_Evil.Choice.CHANGE;
                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[4], false, true, false, false);
                        }
                        break;
                    case 2:
                        this.choice = LivingWall_Evil.Choice.GROW;
                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, OPTIONS[5], true, false, false, false);
                        }
                        break;
                    case 3:
                        this.choice = LivingWall_Evil.Choice.GROW;
                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, OPTIONS[5], true, false, false, false);
                        }
                }

                this.pickCard = true;
                this.imageEventText.updateBodyText(RESULT_DIALOG);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[6]);
                this.screen = LivingWall_Evil.CurScreen.RESULT;
                break;
            default:
                this.openMap();
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Living Wall");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        RESULT_DIALOG = DESCRIPTIONS[1];
    }

    private static enum Choice {
        FORGET,
        CHANGE,
        GROW,
        FIGHT;

        private Choice() {
        }
    }

    private static enum CurScreen {
        INTRO,
        RESULT;

        private CurScreen() {
        }
    }
}
