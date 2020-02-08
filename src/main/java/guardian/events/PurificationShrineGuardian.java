package guardian.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import guardian.GuardianMod;

import java.util.ArrayList;

public class PurificationShrineGuardian extends AbstractImageEvent {
    public static final String ID = "Guardian:Purifier";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String IGNORE;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Purifier");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        IGNORE = DESCRIPTIONS[2];
    }

    private PurificationShrineGuardian.CUR_SCREEN screen;

    public PurificationShrineGuardian() {
        super(NAME, DIALOG_1, "images/events/shrine3.jpg");
        this.screen = PurificationShrineGuardian.CUR_SCREEN.INTRO;
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
            CardCrawlGame.sound.play("CARD_EXHAUST");
            logMetricCardRemoval("Purifier", "Purged", (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0));
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect((AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
            AbstractDungeon.player.masterDeck.removeCard((AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.screen = PurificationShrineGuardian.CUR_SCREEN.COMPLETE;
                        this.imageEventText.updateBodyText(CardCrawlGame.languagePack.getEventString("Guardian:Purifier").DESCRIPTIONS[0]);
                        ArrayList<AbstractCard> gems = GuardianMod.getRewardGemCards(false, 2);
                        ArrayList<AbstractCard> rewards = new ArrayList<>();
                        int rando;
                        for (int i = 0; i < 2; ++i) {
                            rando = AbstractDungeon.cardRng.random(gems.size() - 1);
                            rewards.add(gems.get(rando));
                            gems.remove(rando);
                        }

                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rewards.get(0), (float) (Settings.WIDTH * 0.35), (float) (Settings.HEIGHT / 2)));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rewards.get(1), (float) (Settings.WIDTH * 0.7), (float) (Settings.HEIGHT / 2)));

                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 1:
                        this.screen = PurificationShrineGuardian.CUR_SCREEN.COMPLETE;
                        this.imageEventText.updateBodyText(DIALOG_2);
                        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[2], false, false, false, true);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 2:
                        this.screen = PurificationShrineGuardian.CUR_SCREEN.COMPLETE;
                        logMetricIgnored("Purifier");
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

    private static enum CUR_SCREEN {
        INTRO,
        COMPLETE;

        private CUR_SCREEN() {
        }
    }
}
