package downfall.events.shrines_evil;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;

import java.util.ArrayList;

public class PurificationShrineEvil extends com.megacrit.cardcrawl.events.AbstractImageEvent {
    public static final String ID = downfallMod.makeID("Purifier");

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Purifier");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    public static String[] DESCRIPTIONSALT;
    public static String[] OPTIONSALT;

    private static final String DIALOG_1 = DESCRIPTIONS[0];
    private static final String DIALOG_2 = DESCRIPTIONS[1];
    private static final String IGNORE = DESCRIPTIONS[2];
    private CUR_SCREEN screen = CUR_SCREEN.INTRO;

    private static enum CUR_SCREEN {
        INTRO, COMPLETE;

        private CUR_SCREEN() {
        }
    }

    public PurificationShrineEvil() {
        super(NAME, DIALOG_1, "images/events/shrine3.jpg");
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:EvilShrines").DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:EvilShrines").OPTIONS;
        this.imageEventText.setDialogOption(OPTIONSALT[1]);
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void onEnterRoom() {
        CardCrawlGame.music.playTempBGM("SHRINE");
    }

    public void update() {
        super.update();
        if ((!AbstractDungeon.isScreenUp) && (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())) {
            CardCrawlGame.sound.play("CARD_EXHAUST");
            AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(

                    (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0), com.megacrit.cardcrawl.core.Settings.WIDTH * 0.25F, com.megacrit.cardcrawl.core.Settings.HEIGHT / 2));


            AbstractDungeon.player.masterDeck.removeCard((AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0));
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() > 1){
                AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(

                        (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(1), com.megacrit.cardcrawl.core.Settings.WIDTH * 0.5F, com.megacrit.cardcrawl.core.Settings.HEIGHT / 2));


                AbstractDungeon.player.masterDeck.removeCard((AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(1));

            } if (AbstractDungeon.gridSelectScreen.selectedCards.size() > 2){
                AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(

                        (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(2), com.megacrit.cardcrawl.core.Settings.WIDTH * 0.75F, com.megacrit.cardcrawl.core.Settings.HEIGHT / 2));

                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCurse().makeStatEquivalentCopy(), (float) (Settings.WIDTH * .5F), (float) (Settings.HEIGHT * .75F)));// 66

                AbstractDungeon.player.masterDeck.removeCard((AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(2));

            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.screen = CUR_SCREEN.COMPLETE;
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[0]);
                        AbstractDungeon.gridSelectScreen.open(
                                com.megacrit.cardcrawl.cards.CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck
                                        .getPurgeableCards()), 3, OPTIONS[2], false, false, false, true);


                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        break;
                    case 1:
                        this.screen = CUR_SCREEN.COMPLETE;
                        this.imageEventText.updateBodyText(DIALOG_2);
                        AbstractDungeon.gridSelectScreen.open(
                                com.megacrit.cardcrawl.cards.CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck
                                        .getPurgeableCards()), 1, OPTIONS[2], false, false, false, true);


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
}


