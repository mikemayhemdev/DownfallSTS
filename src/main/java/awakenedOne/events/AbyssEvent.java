package awakenedOne.events;

import awakenedOne.cards.altDimension.RealityRift;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.cards.curses.Aged;

import java.util.ArrayList;

public class AbyssEvent extends AbstractImageEvent {
    public static final String ID = "awakened:AbyssEvent";
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

    private int screenNum = 0;
    private final boolean pickCard = false;
    private final int hpCost = 5;
    private final int hpSpent = 0;
    private final ArrayList<String> cardsTeched = new ArrayList<>();
    private int prideGained;

    public AbyssEvent() {
        super(NAME, DESCRIPTIONS[0], "champResources/images/events/book.png");


        this.imageEventText.setDialogOption(OPTIONS[0], new RealityRift());
        this.imageEventText.setDialogOption(OPTIONS[1]);

    }


    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.screenNum = 1;
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        AbstractCard card = new RealityRift();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, (float) (Settings.WIDTH * .33), (float) (Settings.HEIGHT / 2)));
                        card = new Aged();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, (float) (Settings.WIDTH * .66), (float) (Settings.HEIGHT / 2)));

                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);

                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 1:
                        this.screenNum = 1;
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);

                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);

                        this.imageEventText.clearRemainingOptions();
                        return;
                }
            case 1:

                this.openMap();
        }

    }

}
