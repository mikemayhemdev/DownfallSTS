
package downfall.events.shrines_evil;


import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.Pain;
import downfall.cards.curses.Aged;
import downfall.cards.curses.Malfunctioning;
import downfall.cards.curses.Sapped;
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

    private boolean cardsSelected = false;

    private int amountneeded = 1;

    private List<String> removedCards = new ArrayList<String>();
    private List<String> obtainedCards = new ArrayList<String>();

    private boolean bonusShrine;
    private boolean bonusShrine2;
    private boolean bonusShrine3;


    public TransmogrifierEvil() {
        super(NAME, DIALOG_1, "images/events/shrine1.jpg");
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:EvilShrines").DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:EvilShrines").OPTIONS;


        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Iterator var2 = AbstractDungeon.player.masterDeck.getPurgeableCards().group.iterator();

        while(var2.hasNext()) {
            AbstractCard card = (AbstractCard)var2.next();
            tmp.addToTop(card);
        }

        if (tmp.size() >= 3) {
            if (AbstractDungeon.ascensionLevel < 15) {
                this.imageEventText.setDialogOption(OPTIONSALT[3], new Malfunctioning());
            } else {
                this.imageEventText.setDialogOption(OPTIONSALT[5], new Aged());
            }
        } else {
            this.imageEventText.setDialogOption(OPTIONSALT[6], true);
        }

        if (tmp.size() >= 1) {
            this.imageEventText.setDialogOption(OPTIONS[0]);
        } else {
            this.imageEventText.setDialogOption(OPTIONSALT[6], true);
        }
        this.imageEventText.setDialogOption(OPTIONS[1]);

    }

    public void onEnterRoom() {

        com.megacrit.cardcrawl.core.CardCrawlGame.music.playTempBGM("SHRINE");

    }


    public void update() {
        super.update();
        if (!this.cardsSelected) {
            List<String> transformedCards = new ArrayList();
            List<String> obtainedCards = new ArrayList();
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() == amountneeded) {
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
                    if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.TRANSFORM && c != null) {
                        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(c.makeCopy(), (float) Settings.WIDTH / 3.0F + displayCount, (float) Settings.HEIGHT / 2.0F, false));
                        displayCount += (float) Settings.WIDTH / 6.0F;
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                if (amountneeded == 3) {
                    AbstractCard curse = new Malfunctioning();
                    if (AbstractDungeon.ascensionLevel >= 15) {
                        curse = new Aged();
                    }
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) (Settings.WIDTH * .5F), (float) (Settings.HEIGHT / 2)));// 66
                    obtainedCards.add(curse.cardID);
                    logMetricTransformCards(ID, "Desecrated", removedCards, obtainedCards);

                } else {
                    logMetricTransformCards(ID, "Transformed", removedCards, obtainedCards);
                }
                AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
            }
        }
}

    protected void buttonEffect(int buttonPressed) {

        switch (this.screen) {

            case INTRO:

                switch (buttonPressed) {

                    case 0:
                        removedCards.clear();
                        obtainedCards.clear();
                        amountneeded = 3;
                        this.transform();
                        this.screen = CUR_SCREEN.COMPLETE;
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[0]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);

                        this.imageEventText.clearRemainingOptions();

                        break;
                    case 1:
                        removedCards.clear();
                        obtainedCards.clear();
                        transform();
                        this.screen = CUR_SCREEN.COMPLETE;
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

    private void transform() {
        if (!AbstractDungeon.isScreenUp) {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), amountneeded, OPTIONS[5], false, false, false, false);
        } else {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), amountneeded, OPTIONS[5], false, false, false, false);
        }
    }


    private static enum CUR_SCREEN {
        INTRO, COMPLETE;


        private CUR_SCREEN() {
        }
    }

}


