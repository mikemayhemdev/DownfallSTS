//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import guardian.GuardianMod;

import java.util.ArrayList;

public class TransmogrifierGuardian extends AbstractImageEvent {
    public static final String ID = "Guardian:Transmorgrifier";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String IGNORE;
    private TransmogrifierGuardian.CUR_SCREEN screen;

    public TransmogrifierGuardian() {
        super(NAME, DIALOG_1, "images/events/shrine1.jpg");
        this.screen = TransmogrifierGuardian.CUR_SCREEN.INTRO;
        this.imageEventText.setDialogOption(CardCrawlGame.languagePack.getEventString("Guardian:Purifier").OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void onEnterRoom() {
        CardCrawlGame.music.playTempBGM("SHRINE");
    }

    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.transformCard(c);
            AbstractCard transCard = AbstractDungeon.getTransformedCard();
            logMetricTransformCard("Transmorgrifier", "Transformed", c, transCard);
            AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(transCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
            case INTRO:
                switch(buttonPressed) {
                    case 0:
                        this.screen = TransmogrifierGuardian.CUR_SCREEN.COMPLETE;
                        this.imageEventText.updateBodyText(CardCrawlGame.languagePack.getEventString("Guardian:Purifier").DESCRIPTIONS[0]);
                        ArrayList<AbstractCard> gems = GuardianMod.getRewardGemCards(false, 2);
                        ArrayList<AbstractCard> rewards = new ArrayList<>();
                        int rando;
                        for(int i = 0; i < 2; ++i) {
                            rando = AbstractDungeon.cardRng.random(gems.size() - 1);
                            rewards.add(gems.get(rando));
                            gems.remove(rando);
                        }

                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rewards.get(0), (float)(Settings.WIDTH * 0.35), (float)(Settings.HEIGHT / 2)));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rewards.get(1), (float)(Settings.WIDTH * 0.7), (float)(Settings.HEIGHT / 2)));

                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 1:
                        this.screen = TransmogrifierGuardian.CUR_SCREEN.COMPLETE;
                        this.imageEventText.updateBodyText(DIALOG_2);
                        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[2], false, true, false, false);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 2:
                        this.screen = TransmogrifierGuardian.CUR_SCREEN.COMPLETE;
                        logMetricIgnored("Transmorgrifier");
                        this.imageEventText.updateBodyText(IGNORE);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    default:
                        return;
                }
            case COMPLETE:
                this.openMap();
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Transmorgrifier");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        IGNORE = DESCRIPTIONS[2];
    }

    private static enum CUR_SCREEN {
        INTRO,
        COMPLETE;

        private CUR_SCREEN() {
        }
    }
}
