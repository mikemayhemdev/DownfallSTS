
package downfall.events.shrines_evil;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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


public class UpgradeShrineEvil extends AbstractImageEvent {
    public static final String ID = downfallMod.makeID("Upgrade Shrine");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Upgrade Shrine");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    public static String[] DESCRIPTIONSALT;
    public static String[] OPTIONSALT;

    private static final String DIALOG_1 = DESCRIPTIONS[0];
    private static final String DIALOG_2 = DESCRIPTIONS[1];
    private static final String IGNORE = DESCRIPTIONS[2];

    private boolean bonusShrine;
    private boolean bonusShrine2;

    private CUR_SCREEN screen = CUR_SCREEN.INTRO;

    private static enum CUR_SCREEN {
        INTRO, COMPLETE;

        private CUR_SCREEN() {
        }
    }

    public UpgradeShrineEvil() {
        super(NAME, DIALOG_1, "images/events/shrine2.jpg");
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:EvilShrines").DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:EvilShrines").OPTIONS;

        if (AbstractDungeon.player.masterDeck.getUpgradableCards().size() >= 2) {
            this.imageEventText.setDialogOption(OPTIONSALT[3]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[3], true);
        }

        if (AbstractDungeon.player.masterDeck.hasUpgradableCards().booleanValue()) {
            this.imageEventText.setDialogOption(OPTIONS[0]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[3], true);
        }
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void onEnterRoom() {
        CardCrawlGame.music.playTempBGM("SHRINE");
    }

    public void update() {
        super.update();
        if ((!AbstractDungeon.isScreenUp) && (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())) {
            AbstractCard c = (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            c.upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(c);
            AbstractDungeon.effectsQueue.add(new com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.UpgradeShineEffect(com.megacrit.cardcrawl.core.Settings.WIDTH * .25F, com.megacrit.cardcrawl.core.Settings.HEIGHT / 2.0F));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            if (bonusShrine2){
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(CardLibrary.getCurse().makeStatEquivalentCopy(), (float) (Settings.WIDTH * .5F), (float) (Settings.HEIGHT / 2)));// 66
            }
            if (bonusShrine){
                bonusShrine = false;
                bonusShrine2 = true;
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck
                        .getUpgradableCards(), 1, OPTIONS[2], true, false, false, false);


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
                        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[0]);
                        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck
                                .getUpgradableCards(), 1, OPTIONS[2], true, false, false, false);


                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        break;
                    case 1:
                        this.screen = CUR_SCREEN.COMPLETE;
                        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
                        this.imageEventText.updateBodyText(DIALOG_2);
                        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck
                                .getUpgradableCards(), 1, OPTIONS[2], true, false, false, false);


                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        break;
                    case 2:
                        this.screen = CUR_SCREEN.COMPLETE;
                        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
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


