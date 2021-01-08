//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import slimebound.relics.GreedOozeRelic;

public class WorldOfGoopSlimebound extends AbstractImageEvent {
    public static final String ID = "Slimebound:WorldofGoop";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String DIALOG_1;
    private static final String GOLD_DIALOG;
    private static final String LEAVE_DIALOG;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Slimebound:WorldofGoop");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        GOLD_DIALOG = DESCRIPTIONS[1];
        LEAVE_DIALOG = DESCRIPTIONS[2];
    }

    private WorldOfGoopSlimebound.CurScreen screen;
    private int damage;
    private int gold;
    private int goldLoss;

    public WorldOfGoopSlimebound() {
        super(NAME, DIALOG_1, "images/events/goopPuddle.jpg");
        this.screen = WorldOfGoopSlimebound.CurScreen.INTRO;
        this.damage = 11;
        this.gold = 75;


        this.imageEventText.setDialogOption(OPTIONS[0]);

        if (this.gold <= AbstractDungeon.player.gold) {
            this.imageEventText.setDialogOption( OPTIONS[1], new GreedOozeRelic());
        } else {
            this.imageEventText.setDialogOption( OPTIONS[3], true);

        }
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_SPIRITS");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(GOLD_DIALOG);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[2]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.gold));
                        AbstractDungeon.player.gainGold(this.gold);
                        imageEventText.updateBodyText(GOLD_DIALOG);
                        this.screen = WorldOfGoopSlimebound.CurScreen.RESULT;
                        return;
                    case 1:
                        imageEventText.updateBodyText(LEAVE_DIALOG);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[2]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, RelicLibrary.getRelic(GreedOozeRelic.ID).makeCopy());
                        AbstractDungeon.player.loseGold(this.gold);
                        this.screen = WorldOfGoopSlimebound.CurScreen.RESULT;
                        return;
                    default:

                        return;
                }
            default:
                this.openMap();
        }
    }

    private enum CurScreen {
        INTRO,
        RESULT;

        CurScreen() {
        }
    }
}
