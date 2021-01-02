
package downfall.events.shrines_evil;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;

import java.util.ArrayList;


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


    private boolean bonusShrine;
    private boolean bonusShrine2;
    private boolean bonusShrine3;


    public TransmogrifierEvil() {
        super(NAME, DIALOG_1, "images/events/shrine1.jpg");
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:EvilShrines").DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:EvilShrines").OPTIONS;

        this.imageEventText.setDialogOption(OPTIONSALT[2]);
        this.imageEventText.setDialogOption(OPTIONS[0]);

        this.imageEventText.setDialogOption(OPTIONS[1]);

    }

    public void onEnterRoom() {

        com.megacrit.cardcrawl.core.CardCrawlGame.music.playTempBGM("SHRINE");

    }

    public void update() {

        super.update();

        if ((!AbstractDungeon.isScreenUp) && (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())) {
            AbstractCard c = (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);
            AbstractCard transCard = AbstractDungeon.getTransformedCard();
            AbstractDungeon.effectsQueue.add(new com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect(transCard, com.megacrit.cardcrawl.core.Settings.WIDTH * 0.25F, com.megacrit.cardcrawl.core.Settings.HEIGHT / 2.0F));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            if (bonusShrine3){
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCurse().makeStatEquivalentCopy(), (float) (Settings.WIDTH * .5F), (float) (Settings.HEIGHT / 2)));// 66
            }
            if (bonusShrine && !bonusShrine2){
                bonusShrine = false;
                bonusShrine2 = true;
                AbstractDungeon.gridSelectScreen.open(
                        com.megacrit.cardcrawl.cards.CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck
                                .getPurgeableCards()), 1, OPTIONS[2], false, true, false, false);

            } else if (bonusShrine2){
                bonusShrine = false;
                bonusShrine2 = false;
                bonusShrine3 = true;
                AbstractDungeon.gridSelectScreen.open(
                        com.megacrit.cardcrawl.cards.CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck
                                .getPurgeableCards()), 1, OPTIONS[2], false, true, false, false);

            }
        }

    }

    protected void buttonEffect(int buttonPressed) {

        switch (this.screen) {

            case INTRO:

                switch (buttonPressed) {

                    case 0:

                        bonusShrine = true;
                        this.screen = CUR_SCREEN.COMPLETE;

                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[0]);

                        AbstractDungeon.gridSelectScreen.open(
                                com.megacrit.cardcrawl.cards.CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck
                                        .getPurgeableCards()), 1, OPTIONS[2], false, true, false, false);


                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);

                        this.imageEventText.clearRemainingOptions();

                        break;
                    case 1:

                        this.screen = CUR_SCREEN.COMPLETE;

                        this.imageEventText.updateBodyText(DIALOG_2);

                        AbstractDungeon.gridSelectScreen.open(
                                com.megacrit.cardcrawl.cards.CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck
                                        .getPurgeableCards()), 1, OPTIONS[2], false, true, false, false);


                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);

                        this.imageEventText.clearRemainingOptions();

                        break;

                    case 2:

                        this.screen = CUR_SCREEN.COMPLETE;


                        this.imageEventText.updateBodyText(IGNORE);

                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);

                        this.imageEventText.clearRemainingOptions();

                }


                break;

            case COMPLETE:

                openMap();

        }

    }


    private static enum CUR_SCREEN {
        INTRO, COMPLETE;


        private CUR_SCREEN() {
        }
    }

}


