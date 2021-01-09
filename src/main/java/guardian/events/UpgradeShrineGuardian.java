package guardian.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import guardian.GuardianMod;

import java.util.ArrayList;

public class UpgradeShrineGuardian extends AbstractImageEvent {
    public static final String ID = "Guardian:UpgradeShrineEvil";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String IGNORE;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Upgrade Shrine");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        IGNORE = DESCRIPTIONS[2];
    }

    private UpgradeShrineGuardian.CUR_SCREEN screen;

    public UpgradeShrineGuardian() {
        super(NAME, DIALOG_1, "images/events/shrine2.jpg");
        this.screen = UpgradeShrineGuardian.CUR_SCREEN.INTRO;

        this.imageEventText.setDialogOption(CardCrawlGame.languagePack.getEventString("Guardian:Purifier").OPTIONS[0]);
        if (AbstractDungeon.player.masterDeck.hasUpgradableCards()) {
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
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            c.upgrade();
            logMetricCardUpgrade("Upgrade Shrine", "Upgraded", c);
            AbstractDungeon.player.bottledCardUpgradeCheck(c);
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.screen = UpgradeShrineGuardian.CUR_SCREEN.COMPLETE;
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
                        this.screen = UpgradeShrineGuardian.CUR_SCREEN.COMPLETE;
                        AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
                        this.imageEventText.updateBodyText(DIALOG_2);
                        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, OPTIONS[2], true, false, false, false);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 2:
                        this.screen = UpgradeShrineGuardian.CUR_SCREEN.COMPLETE;
                        AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
                        logMetricIgnored("Upgrade Shrine");
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

    private enum CUR_SCREEN {
        INTRO,
        COMPLETE;

        CUR_SCREEN() {
        }
    }
}
