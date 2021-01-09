//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package downfall.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.exordium.GremlinFat;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.relics.GremlinSack;
import slimebound.SlimeboundMod;

import java.util.*;

public class GremlinMatchGame_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:GremlinMatchGame";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String MSG_2;
    private static final String MSG_3;
    private static final String MSG_4;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        MSG_2 = DESCRIPTIONS[0];
        MSG_3 = DESCRIPTIONS[1];
        MSG_4 = DESCRIPTIONS[3];
    }

    ArrayList<AbstractCard> singleCards = new ArrayList();
    private AbstractCard chosenCard;
    private AbstractCard hoveredCard;
    private boolean cardFlipped = false;
    private boolean gameDone = false;
    private boolean cleanUpCalled = false;
    private boolean threatened = false;
    private int attemptCount = 5;
    private CardGroup cards;
    private float waitTimer;
    private int cardsMatched;
    private GremlinMatchGame_Evil.CUR_SCREEN screen;
    private List<String> matchedCards;

    public GremlinMatchGame_Evil() {
        super(NAME, DESCRIPTIONS[2], "images/events/matchAndKeep.jpg");
        this.cards = new CardGroup(CardGroupType.UNSPECIFIED);
        this.waitTimer = 0.0F;
        this.cardsMatched = 0;
        this.screen = GremlinMatchGame_Evil.CUR_SCREEN.INTRO;
        this.cards.group = this.initializeCards();
        Collections.shuffle(this.cards.group, new Random(AbstractDungeon.miscRng.randomLong()));
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.matchedCards = new ArrayList();
    }

    private ArrayList<AbstractCard> initializeCards() {
        ArrayList<AbstractCard> retVal = new ArrayList();
        ArrayList<AbstractCard> retVal2 = new ArrayList();
        if (AbstractDungeon.ascensionLevel >= 15) {
            retVal.add(AbstractDungeon.getCard(CardRarity.RARE).makeCopy());
            retVal.add(AbstractDungeon.getCard(CardRarity.UNCOMMON).makeCopy());
            retVal.add(AbstractDungeon.getCard(CardRarity.COMMON).makeCopy());
            retVal.add(AbstractDungeon.returnRandomCurse());
            retVal.add(AbstractDungeon.returnRandomCurse());
        } else {
            retVal.add(AbstractDungeon.getCard(CardRarity.RARE).makeCopy());
            retVal.add(AbstractDungeon.getCard(CardRarity.UNCOMMON).makeCopy());
            retVal.add(AbstractDungeon.getCard(CardRarity.COMMON).makeCopy());
            retVal.add(AbstractDungeon.returnColorlessCard(CardRarity.UNCOMMON).makeCopy());
            retVal.add(AbstractDungeon.returnRandomCurse());
        }

        retVal.add(AbstractDungeon.player.getStartCardForEvent());
        Iterator var3 = retVal.iterator();

        AbstractCard c;
        while (var3.hasNext()) {
            c = (AbstractCard) var3.next();
            Iterator var5 = AbstractDungeon.player.relics.iterator();

            while (var5.hasNext()) {
                AbstractRelic r = (AbstractRelic) var5.next();
                r.onPreviewObtainCard(c);
            }

            retVal2.add(c.makeStatEquivalentCopy());
        }

        singleCards = retVal2;
        retVal.addAll(retVal2);

        for (var3 = retVal.iterator(); var3.hasNext(); c.target_y = c.current_y) {
            c = (AbstractCard) var3.next();
            c.current_x = (float) Settings.WIDTH / 2.0F;
            c.target_x = c.current_x;
            c.current_y = -300.0F * Settings.scale;
        }

        return retVal;
    }

    public void update() {
        super.update();
        this.cards.update();
        if (this.screen == GremlinMatchGame_Evil.CUR_SCREEN.PLAY) {
            this.updateControllerInput();
            this.updateMatchGameLogic();
        } else if (this.screen == GremlinMatchGame_Evil.CUR_SCREEN.CLEAN_UP) {
            if (!this.cleanUpCalled) {
                this.cleanUpCalled = true;
                this.cleanUpCards();
            }

            if (this.waitTimer > 0.0F && !threatened) {
                this.waitTimer -= Gdx.graphics.getDeltaTime();
                if (this.waitTimer < 0.0F) {
                    this.waitTimer = 0.0F;
                    //SlimeboundMod.logger.info("Complete screen being set");
                    this.screen = CUR_SCREEN.COMPLETE;
                    GenericEventDialog.show();
                    this.imageEventText.updateBodyText(MSG_3);
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.setDialogOption(OPTIONS[1]);
                }
            }
        }

        if (!GenericEventDialog.waitForInput) {
            this.buttonEffect(GenericEventDialog.getSelectedOption());
        }

    }

    private void updateControllerInput() {
        if (Settings.isControllerMode) {
            boolean anyHovered = false;
            int index = 0;

            for (Iterator var3 = this.cards.group.iterator(); var3.hasNext(); ++index) {
                AbstractCard c = (AbstractCard) var3.next();
                if (c.hb.hovered) {
                    anyHovered = true;
                    break;
                }
            }

            if (!anyHovered) {
                Gdx.input.setCursorPosition((int) this.cards.group.get(0).hb.cX, Settings.HEIGHT - (int) this.cards.group.get(0).hb.cY);
            } else {
                float x;
                if (!CInputActionSet.up.isJustPressed() && !CInputActionSet.altUp.isJustPressed()) {
                    if (!CInputActionSet.down.isJustPressed() && !CInputActionSet.altDown.isJustPressed()) {
                        if (!CInputActionSet.left.isJustPressed() && !CInputActionSet.altLeft.isJustPressed()) {
                            if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
                                x = this.cards.group.get(index).hb.cX + 210.0F * Settings.scale;
                                if (x > 1375.0F * Settings.scale) {
                                    x = 640.0F * Settings.scale;
                                }

                                Gdx.input.setCursorPosition((int) x, Settings.HEIGHT - (int) this.cards.group.get(index).hb.cY);
                            }
                        } else {
                            x = this.cards.group.get(index).hb.cX - 210.0F * Settings.scale;
                            if (x < 530.0F * Settings.scale) {
                                x = 1270.0F * Settings.scale;
                            }

                            Gdx.input.setCursorPosition((int) x, Settings.HEIGHT - (int) this.cards.group.get(index).hb.cY);
                        }
                    } else {
                        x = this.cards.group.get(index).hb.cY - 230.0F * Settings.scale;
                        if (x < 175.0F * Settings.scale) {
                            x = 750.0F * Settings.scale;
                        }

                        Gdx.input.setCursorPosition((int) this.cards.group.get(index).hb.cX, (int) ((float) Settings.HEIGHT - x));
                    }
                } else {
                    x = this.cards.group.get(index).hb.cY + 230.0F * Settings.scale;
                    if (x > 865.0F * Settings.scale) {
                        x = 290.0F * Settings.scale;
                    }

                    Gdx.input.setCursorPosition((int) this.cards.group.get(index).hb.cX, (int) ((float) Settings.HEIGHT - x));
                }

                if (CInputActionSet.select.isJustPressed()) {
                    CInputActionSet.select.unpress();
                    InputHelper.justClickedLeft = true;
                }
            }

        }
    }

    private void cleanUpCards() {
        AbstractCard c;
        for (Iterator var1 = this.cards.group.iterator(); var1.hasNext(); c.target_y = -300.0F * Settings.scale) {
            c = (AbstractCard) var1.next();
            c.targetDrawScale = 0.5F;
            c.target_x = (float) Settings.WIDTH / 2.0F;
        }

    }

    private void updateMatchGameLogic() {
        if (this.waitTimer == 0.0F) {
            this.hoveredCard = null;
            Iterator var1 = this.cards.group.iterator();

            while (true) {
                while (var1.hasNext()) {
                    AbstractCard c = (AbstractCard) var1.next();
                    c.hb.update();
                    if (this.hoveredCard == null && c.hb.hovered) {
                        c.drawScale = 0.7F;
                        c.targetDrawScale = 0.7F;
                        this.hoveredCard = c;
                        if (InputHelper.justClickedLeft && this.hoveredCard.isFlipped) {
                            InputHelper.justClickedLeft = false;
                            this.hoveredCard.isFlipped = false;
                            if (!this.cardFlipped) {
                                this.cardFlipped = true;
                                this.chosenCard = this.hoveredCard;
                            } else {
                                this.cardFlipped = false;
                                if (this.chosenCard.cardID.equals(this.hoveredCard.cardID)) {
                                    this.waitTimer = 1.0F;
                                    this.chosenCard.targetDrawScale = 0.7F;
                                    this.chosenCard.target_x = (float) Settings.WIDTH / 2.0F;
                                    this.chosenCard.target_y = (float) Settings.HEIGHT / 2.0F;
                                    this.hoveredCard.targetDrawScale = 0.7F;
                                    this.hoveredCard.target_x = (float) Settings.WIDTH / 2.0F;
                                    this.hoveredCard.target_y = (float) Settings.HEIGHT / 2.0F;
                                } else {
                                    this.waitTimer = 1.25F;
                                    this.chosenCard.targetDrawScale = 1.0F;
                                    this.hoveredCard.targetDrawScale = 1.0F;
                                }
                            }
                        }
                    } else if (c != this.chosenCard) {
                        c.targetDrawScale = 0.5F;
                    }
                }

                return;
            }
        } else {
            this.waitTimer -= Gdx.graphics.getDeltaTime();
            if (this.waitTimer < 0.0F && !this.gameDone) {
                this.waitTimer = 0.0F;
                if (this.chosenCard.cardID.equals(this.hoveredCard.cardID)) {
                    ++this.cardsMatched;
                    this.cards.group.remove(this.chosenCard);
                    this.cards.group.remove(this.hoveredCard);
                    this.matchedCards.add(this.chosenCard.cardID);
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.chosenCard.makeCopy(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    this.chosenCard = null;
                    this.hoveredCard = null;
                } else {
                    this.chosenCard.isFlipped = true;
                    this.hoveredCard.isFlipped = true;
                    this.chosenCard.targetDrawScale = 0.5F;
                    this.hoveredCard.targetDrawScale = 0.5F;
                    this.chosenCard = null;
                    this.hoveredCard = null;
                }

                --this.attemptCount;
                if (this.attemptCount == 0) {
                    this.gameDone = true;
                    this.waitTimer = 1.0F;
                }
            } else if (this.gameDone) {
                this.screen = GremlinMatchGame_Evil.CUR_SCREEN.CLEAN_UP;
            }
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case COMPLETE:
                switch (buttonPressed) {
                    case 0:
                        //SlimeboundMod.logger.info("case default opening map");
                        this.openMap();
                        return;

                }
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(MSG_2);
                        this.imageEventText.removeDialogOption(1);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        //SlimeboundMod.logger.info("case intro opening rule explanation");
                        this.screen = GremlinMatchGame_Evil.CUR_SCREEN.RULE_EXPLANATION;
                        return;
                }
            case RULE_EXPLANATION:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.removeDialogOption(0);
                        this.imageEventText.removeDialogOption(1);
                        this.imageEventText.clearRemainingOptions();
                        GenericEventDialog.hide();
                        this.screen = CUR_SCREEN.PLAY;
                        this.threatened = false;
                        //SlimeboundMod.logger.info("case rule explanation opening cards");
                        this.placeCards();
                        return;
                    case 1:
                        if (!this.threatened) {
                            this.screen = CUR_SCREEN.RULE_EXPLANATION;
                            this.imageEventText.updateBodyText(MSG_4);
                            //SlimeboundMod.logger.info("threatened");
                            imageEventText.removeDialogOption(1);
                            this.imageEventText.setDialogOption(OPTIONS[5],  new GremlinSack());
                            this.threatened = true;
                            return;
                        } else {
                            this.screen = CUR_SCREEN.FIGHT;
                            //SlimeboundMod.logger.info("fight");
                            MonsterGroup monsters = new MonsterGroup(new GremlinFat(-400F, 0F));
                            monsters.add(new GremlinNob(0F, 0F));
                            AbstractDungeon.getCurrRoom().monsters = monsters;
                            AbstractDungeon.getCurrRoom().rewards.clear();
                            GremlinSack r = new GremlinSack();
                            r.onTrigger();
                            AbstractDungeon.getCurrRoom().addRelicToRewards(r);
                            AbstractDungeon.getCurrRoom().addGoldToRewards(100);

                            AbstractDungeon.getCurrRoom().eliteTrigger = true;
                            this.imageEventText.clearRemainingOptions();
                            this.enterCombatFromImage();
                            return;
                        }
                }


        }

    }

    private void placeCards() {
        for (int i = 0; i < this.cards.size(); ++i) {
            this.cards.group.get(i).target_x = (float) (i % 4) * 210.0F * Settings.scale + 640.0F * Settings.scale;
            this.cards.group.get(i).target_y = (float) (i % 3) * -230.0F * Settings.scale + 750.0F * Settings.scale;
            this.cards.group.get(i).targetDrawScale = 0.5F;
            this.cards.group.get(i).isFlipped = true;
        }

    }

    public void render(SpriteBatch sb) {
        this.cards.render(sb);
        if (this.chosenCard != null) {
            this.chosenCard.render(sb);
        }

        if (this.hoveredCard != null) {
            this.hoveredCard.render(sb);
        }

        if (this.screen == GremlinMatchGame_Evil.CUR_SCREEN.PLAY) {
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, OPTIONS[3] + this.attemptCount, 780.0F * Settings.scale, 80.0F * Settings.scale, 2000.0F * Settings.scale, 0.0F, Color.WHITE);
        }

    }

    public void renderAboveTopPanel(SpriteBatch sb) {
    }

    private enum CUR_SCREEN {
        INTRO,
        RULE_EXPLANATION,
        PLAY,
        COMPLETE,
        FIGHT,
        CLEAN_UP;

        CUR_SCREEN() {
        }
    }
}
