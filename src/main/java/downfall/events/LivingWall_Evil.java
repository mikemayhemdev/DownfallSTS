//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package downfall.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.util.TransformCardReward;
import downfall.util.UpgradeCardReward;
import downfall.util.RemoveCardReward;
import slimebound.SlimeboundMod;

public class LivingWall_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:LivingWall";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] OPTIONSEXTRA;
    private static final EventStrings eventStrings;
    private static final String DIALOG_1;
    private static final String RESULT_DIALOG;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Living Wall");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        OPTIONSEXTRA = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        RESULT_DIALOG = DESCRIPTIONS[1];
    }

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
        this.imageEventText.setDialogOption(OPTIONSEXTRA[0]);

    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_LIVING_WALL");
        }
    }

    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            switch (this.choice) {
                case FORGET:
                    CardCrawlGame.sound.play("CARD_EXHAUST");
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                    AbstractEvent.logMetricCardRemoval("Living Wall", "Forget", AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                    AbstractDungeon.player.masterDeck.removeCard(AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                    break;
                case CHANGE:
                    AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    AbstractDungeon.player.masterDeck.removeCard(c);
                    AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);
                    AbstractCard transCard = AbstractDungeon.getTransformedCard();
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(transCard, c.current_x, c.current_y));
                    AbstractEvent.logMetricTransformCard("Living Wall", "Change", c, transCard);
                    break;
                case GROW:
                    AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    AbstractDungeon.gridSelectScreen.selectedCards.get(0).upgrade();
                    AbstractCard upgCard = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    AbstractEvent.logMetricCardUpgrade("Living Wall", "Grow", upgCard);
                    AbstractDungeon.player.bottledCardUpgradeCheck(upgCard);
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.pickCard = false;
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
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
                        this.choice = Choice.FIGHT;
                        //SlimeboundMod.logger.info("fight");
//                        MonsterGroup monsters =
                                AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("downfall:Heads");
//                        AbstractDungeon.getCurrRoom().monsters = monsters;
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addGoldToRewards(100);
                        AbstractDungeon.getCurrRoom().rewards.add(new RemoveCardReward());
                        AbstractDungeon.getCurrRoom().rewards.add(new UpgradeCardReward());
                        AbstractDungeon.getCurrRoom().rewards.add(new TransformCardReward());
                        AbstractDungeon.getCurrRoom().eliteTrigger = true;
                        this.imageEventText.clearRemainingOptions();
                        this.enterCombatFromImage();
                        AbstractDungeon.lastCombatMetricKey = "downfall:Heads";
                        break;
                }

                if (this.choice != Choice.FIGHT) {
                    this.pickCard = true;
                    this.imageEventText.updateBodyText(RESULT_DIALOG);
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(OPTIONS[6]);
                    this.screen = LivingWall_Evil.CurScreen.RESULT;
                    break;
                }
                break;

            default:
                this.openMap();
        }

    }

    private enum Choice {
        FORGET,
        CHANGE,
        GROW,
        FIGHT;

        Choice() {
        }
    }

    private enum CurScreen {
        INTRO,
        RESULT;

        CurScreen() {
        }
    }
}
