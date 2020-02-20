package evilWithin.events;


import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.city.TheMausoleum;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class Mausoleum_Evil extends AbstractImageEvent {
    public static final String ID = "evilWithin:Mausoleum";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private static final EventStrings eventStrings;
    private static final String DIALOG_1;
    private static final String CURSED_RESULT;
    private static final String NORMAL_RESULT;
    private static final String NOPE_RESULT;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(TheMausoleum.ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];// 21
        CURSED_RESULT = DESCRIPTIONS[1];// 22
        NORMAL_RESULT = DESCRIPTIONS[2];// 23
        NOPE_RESULT = DESCRIPTIONS[3];// 24
    }

    private int screenNum = 0;
    private CurScreen screen;

    public Mausoleum_Evil() {
        super(NAME, DESCRIPTIONSALT[0], "images/events/mausoleum.jpg");
        this.imageEventText.setDialogOption(OPTIONSALT[0], CardLibrary.getCopy(Writhe.ID));
        this.imageEventText.setDialogOption(OPTIONS[4], CardLibrary.getCopy(Writhe.ID));// 38
        this.imageEventText.setDialogOption(OPTIONS[5]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_GHOSTS");
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {// 61
            case INTRO:
                switch (buttonPressed) {// 63
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[1]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Writhe(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));// 73
                        AbstractDungeon.effectList.add(new RainingGoldEffect(200));
                        AbstractDungeon.player.gainGold(200);
                        CardCrawlGame.sound.play("BLUNT_HEAVY");
                    case 1:
                        boolean result = AbstractDungeon.miscRng.randomBoolean();// 66
                        if (AbstractDungeon.ascensionLevel >= 15) {// 67
                            result = true;// 68
                        }

                        if (result) {// 71
                            this.imageEventText.updateBodyText(CURSED_RESULT);// 72
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Writhe(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));// 73
                        } else {
                            this.imageEventText.updateBodyText(NORMAL_RESULT);// 76
                        }

                        CardCrawlGame.sound.play("BLUNT_HEAVY");// 79
                        CardCrawlGame.screenShake.rumble(2.0F);// 80
                        AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());// 81 82
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);// 83
                        if (result) {// 85
                            logMetricObtainCardAndRelic("The Mausoleum", "Opened", new Writhe(), r);// 86
                        } else {
                            logMetricObtainRelic("The Mausoleum", "Opened", r);// 88
                        }
                        break;
                    default:
                        this.imageEventText.updateBodyText(NOPE_RESULT);// 92
                        logMetricIgnored("The Mausoleum");// 93
                }

                this.imageEventText.clearAllDialogs();// 96
                this.imageEventText.setDialogOption(OPTIONS[2]);// 97
                this.screen = CurScreen.RESULT;// 98
                break;// 99
            default:
                this.openMap();// 101
        }

    }// 104

    private enum CurScreen {
        INTRO,
        RESULT;

        CurScreen() {
        }// 30
    }
}
