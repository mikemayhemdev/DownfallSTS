package theHexaghost.events;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.DarkstonePeriapt;
import com.megacrit.cardcrawl.relics.DuVuDoll;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theHexaghost.HexaMod;
import theHexaghost.cards.Haunted;

import java.util.ArrayList;

public class WanderingSpecter extends AbstractImageEvent {
    public static final String ID = HexaMod.makeID("WanderingSpecter");
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
    private AbstractRelic rtog;

    public WanderingSpecter() {
        super(NAME, DESCRIPTIONS[0], "hexamodResources/images/events/specter.png");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;

        ArrayList<String> possRelicsList = new ArrayList<>();
        possRelicsList.add(BlueCandle.ID);
        possRelicsList.add(DarkstonePeriapt.ID);
        possRelicsList.add(DuVuDoll.ID);

        for (AbstractRelic q : AbstractDungeon.player.relics) {
            possRelicsList.removeIf(q.relicId::equals);
        }

        if (possRelicsList.size() > 0) {
            rtog = RelicLibrary.getRelic(possRelicsList.get(AbstractDungeon.cardRandomRng.random(possRelicsList.size() - 1))).makeCopy();
            this.imageEventText.setDialogOption(OPTIONS[0] + rtog.name + OPTIONS[1], new Haunted(), rtog);
        } else {
            imageEventText.setDialogOption(OPTIONS[3], true);
        }

        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.imageEventText.clearAllDialogs();
                switch (buttonPressed) {
                    case 0:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Haunted(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));// 73
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rtog);// 83
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.screen = CurScreen.END;
                        break;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
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
