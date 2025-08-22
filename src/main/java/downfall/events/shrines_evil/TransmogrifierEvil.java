
package downfall.events.shrines_evil;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import downfall.cards.curses.Malfunctioning;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TransmogrifierEvil extends AbstractImageEvent {
    public static final String ID = downfallMod.makeID("Transmorgrifier");
    private static final EventStrings eventStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getEventString("Transmorgrifier");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String DIALOG_1 = DESCRIPTIONS[0];
    private static final String DIALOG_2 = DESCRIPTIONS[1];
    private static final String IGNORE = DESCRIPTIONS[2];
    public static String[] DESCRIPTIONSALT;
    public static String[] OPTIONSALT;
    private CUR_SCREEN screen = CUR_SCREEN.INTRO;

    private List<String> obtainedCards = new ArrayList<String>();

    private boolean cardsSelected = false;
    private int cardCount;
    private int lifeCost;


    public TransmogrifierEvil() {
        super(NAME, DIALOG_1, "images/events/shrine1.jpg");
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:EvilShrines").DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:EvilShrines").OPTIONS;

        if (AbstractDungeon.ascensionLevel >= 15){
            lifeCost = MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.15F);
        } else {
            lifeCost = MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.10F);
        }

        if (AbstractDungeon.player.masterDeck.getPurgeableCards().size() >= 2) {
            this.imageEventText.setDialogOption(OPTIONSALT[2] + lifeCost + OPTIONSALT[5]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[6], true);
        }
        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[0]);

    }

    public void onEnterRoom() {

        com.megacrit.cardcrawl.core.CardCrawlGame.music.playTempBGM("SHRINE");

    }

    public void update() {

        super.update();
            if (cardCount == 1){
                if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    AbstractDungeon.player.masterDeck.removeCard(c);
                    AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);
                    AbstractCard transCard = AbstractDungeon.getTransformedCard();
                    AbstractDungeon.player.damage(new DamageInfo(null, this.lifeCost));
                    logMetricTransformCard("Transmorgrifier", "Transformed", c, transCard);
                    AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(transCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * .66F));
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                }
            }
            else if (AbstractDungeon.gridSelectScreen.selectedCards.size() == cardCount && !this.cardsSelected) {
                this.cardsSelected = true;
                float displayCount = 0.0F;
                Iterator<AbstractCard> i = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                List<String> transformedCards = new ArrayList<>();
                while (i.hasNext()) {
                    AbstractCard card = (AbstractCard) i.next();
                    card.untip();
                    card.unhover();
                    transformedCards.add(card.cardID);
                    AbstractDungeon.player.masterDeck.removeCard(card);
                    AbstractDungeon.transformCard(card, false, AbstractDungeon.miscRng);
                    AbstractCard c = AbstractDungeon.getTransformedCard();
                    obtainedCards.add(c.cardID);
                    if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.TRANSFORM) {
                        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(c.makeCopy(), (float) Settings.WIDTH / 3.0F + displayCount, (float) Settings.HEIGHT *.66F, false));
                        displayCount += (float) Settings.WIDTH / 6.0F;
                    }
                }
               // this.screen = CUR_SCREEN.COMPLETE;
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                logMetricTransformCards(ID, "Became Test Subject", transformedCards, obtainedCards);
                AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
            }
    }

    protected void buttonEffect(int buttonPressed) {

        switch (this.screen) {

            case INTRO:

                switch (buttonPressed) {

                    case 0:
                        cardCount = 2;
                        obtainedCards.clear();

                        this.screen = CUR_SCREEN.COMPLETE;
                        this.transform(cardCount);
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[0]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();

                        break;
                    case 1:
                        cardCount = 1;
                        obtainedCards.clear();

                        this.screen = CUR_SCREEN.COMPLETE;
                        AbstractDungeon.gridSelectScreen.open(
                                com.megacrit.cardcrawl.cards.CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck
                                        .getPurgeableCards()), cardCount, OPTIONS[2], false, true, false, false);


                        this.imageEventText.updateBodyText(DIALOG_2);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);

                        this.imageEventText.clearRemainingOptions();

                        break;

                    case 2:

                        this.screen = CUR_SCREEN.COMPLETE;


                        this.imageEventText.updateBodyText(IGNORE);

                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);

                        this.imageEventText.clearRemainingOptions();
                        logMetricIgnored(ID);

                }


                break;

            case COMPLETE:

                openMap();

        }

    }

    private void transform(int count) {
        if (!AbstractDungeon.isScreenUp) {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), count, CardCrawlGame.languagePack.getEventString("Drug Dealer").OPTIONS[5], false, false, false, false);
        } else {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), count, CardCrawlGame.languagePack.getEventString("Drug Dealer").OPTIONS[5], false, false, false, false);
        }

    }


    private static enum CUR_SCREEN {
        INTRO, COMPLETE;


        private CUR_SCREEN() {
        }
    }

}