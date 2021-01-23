package theHexaghost.events;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.beyond.Falling;
import com.megacrit.cardcrawl.localization.EventStrings;
import theHexaghost.HexaMod;

public class HexaFalling extends AbstractImageEvent {
    public static final String ID = HexaMod.makeID("HexaFalling");
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = CardCrawlGame.languagePack.getEventString(Falling.ID).NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurScreen screen;

    public HexaFalling() {
        super(NAME, CardCrawlGame.languagePack.getEventString(Falling.ID).DESCRIPTIONS[0], "images/events/falling.jpg");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;

        this.imageEventText.setDialogOption(OPTIONS[0]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.imageEventText.clearAllDialogs();
                if (buttonPressed == 0) {
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(OPTIONS[1]);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[0]);
                    this.screen = CurScreen.END;
                }
                break;
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
