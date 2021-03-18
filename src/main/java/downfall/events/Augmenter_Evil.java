package downfall.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.JAX;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.MutagenicStrength;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;
import downfall.util.JaxReward;
import downfall.util.TransformCardReward;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Augmenter_Evil extends AbstractImageEvent {
    public static final String ID = downfallMod.makeID("Augmenter");
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private static final Logger logger = LogManager.getLogger(com.megacrit.cardcrawl.events.city.DrugDealer.class.getName());
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Drug Dealer");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
    }

    private int screenNum = 0;
    private boolean cardsSelected = false;

    public Augmenter_Evil() {
        super(NAME, DESCRIPTIONSALT[0], "images/events/drugDealer.jpg");
        this.imageEventText.setDialogOption(OPTIONS[0], CardLibrary.getCopy("J.A.X."));
        if (AbstractDungeon.player.masterDeck.getPurgeableCards().size() >= 2) {
            this.imageEventText.setDialogOption(OPTIONS[1]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[4], true);
        }

        this.imageEventText.setDialogOption(OPTIONS[2], new MutagenicStrength());
        this.imageEventText.setDialogOption(OPTIONSALT[0]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        AbstractCard jax = new JAX();
                        logMetricObtainCard("Drug Dealer", "Obtain J.A.X.", jax);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(jax, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.transform();
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        Object r;
                        if (!AbstractDungeon.player.hasRelic("MutagenicStrength")) {
                            r = new MutagenicStrength();
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, (AbstractRelic) r);
                        } else {
                            r = new Circlet();
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, (AbstractRelic) r);
                        }

                        logMetricObtainRelic("Drug Dealer", "Inject Mutagens", (AbstractRelic) r);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        break;
                    case 3:
                        //SlimeboundMod.logger.info("fight");

                        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("downfall:Augmenter");
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addGoldToRewards(100);
                        AbstractDungeon.getCurrRoom().addRelicToRewards(new MutagenicStrength());
                        AbstractDungeon.getCurrRoom().rewards.add(new TransformCardReward());
                        AbstractDungeon.getCurrRoom().rewards.add(new TransformCardReward());
                        AbstractDungeon.getCurrRoom().rewards.add(new JaxReward());
                        AbstractDungeon.getCurrRoom().eliteTrigger = true;
                        this.imageEventText.clearRemainingOptions();
                        this.enterCombatFromImage();
                        AbstractDungeon.lastCombatMetricKey = "downfall:Augmenter";
                        break;
                    default:
                        logger.info("ERROR: Unhandled case " + buttonPressed);
                }

                this.screenNum = 1;
                break;
            case 1:
                this.openMap();
        }

    }

    public void update() {
        super.update();
        if (!this.cardsSelected) {
            List<String> transformedCards = new ArrayList();
            List<String> obtainedCards = new ArrayList();
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() == 2) {
                this.cardsSelected = true;
                float displayCount = 0.0F;
                Iterator i = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while (i.hasNext()) {
                    AbstractCard card = (AbstractCard) i.next();
                    card.untip();
                    card.unhover();
                    transformedCards.add(card.cardID);
                    AbstractDungeon.player.masterDeck.removeCard(card);
                    AbstractDungeon.transformCard(card, false, AbstractDungeon.miscRng);
                    AbstractCard c = AbstractDungeon.getTransformedCard();
                    obtainedCards.add(c.cardID);
                    if (AbstractDungeon.screen != CurrentScreen.TRANSFORM && c != null) {
                        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(c.makeCopy(), (float) Settings.WIDTH / 3.0F + displayCount, (float) Settings.HEIGHT / 2.0F, false));
                        displayCount += (float) Settings.WIDTH / 6.0F;
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                logMetricTransformCards("Drug Dealer", "Became Test Subject", transformedCards, obtainedCards);
                AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
            }
        }

    }

    private void transform() {
        if (!AbstractDungeon.isScreenUp) {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 2, OPTIONS[5], false, false, false, false);
        } else {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 2, OPTIONS[5], false, false, false, false);
        }

    }
}
