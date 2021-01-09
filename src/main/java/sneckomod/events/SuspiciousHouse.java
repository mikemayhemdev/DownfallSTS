package sneckomod.events;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.cards.curses.Bewildered;
import sneckomod.relics.BabySnecko;

public class SuspiciousHouse extends AbstractImageEvent {
    public static final String ID = "sneckomod:SuspiciousHouse";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurScreen screen;

    public SuspiciousHouse() {
        super(NAME, DESCRIPTIONS[0], "sneckomodResources/images/events/cityStreet.png");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;

        this.imageEventText.setDialogOption(OPTIONS[0], new Bewildered(), new BabySnecko());
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.imageEventText.clearAllDialogs();
                switch (buttonPressed) {
                    case 0:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Bewildered(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new sneckomod.relics.BabySnecko());
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.screen = CurScreen.END;
                        return;
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.screen = CurScreen.END;
                        return;
                }

            case END:
                this.openMap();
        }

    }

    private enum CurScreen {
        INTRO,
        END;

        CurScreen() {
        }
    }
}
