package downfall.events;


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
import downfall.downfallMod;
import downfall.cards.curses.Haunted;

public class Mausoleum_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:Mausoleum";
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

    private int percent;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(TheMausoleum.ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:Mausoleum").DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString("downfall:Mausoleum").OPTIONS;
        DIALOG_1 = DESCRIPTIONSALT[0];
        CURSED_RESULT = DESCRIPTIONS[1];
        NORMAL_RESULT = DESCRIPTIONS[2];
        NOPE_RESULT = DESCRIPTIONS[3];
    }

    private int screenNum = 0;
    private CurScreen screen;

    public Mausoleum_Evil() {
        super(NAME, DIALOG_1, "images/events/mausoleum.jpg");

        this.screen = CurScreen.INTRO;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.percent = 100;
        } else {
            this.percent = 50;
        }

        this.imageEventText.setDialogOption(OPTIONSALT[0], CardLibrary.getCopy(Haunted.ID));
        this.imageEventText.setDialogOption(OPTIONS[0] + this.percent + OPTIONS[1], CardLibrary.getCopy("Writhe"));
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_GHOSTS");
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[1]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Haunted(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        AbstractDungeon.effectList.add(new RainingGoldEffect(200));
                        AbstractDungeon.player.gainGold(200);
                        this.imageEventText.loadImage(downfallMod.assetPath("images/events/mausoleumNoSpirit.png"));
                        break;
                    case 1:
                        boolean result = AbstractDungeon.miscRng.randomBoolean();
                        if (AbstractDungeon.ascensionLevel >= 15) {
                            result = true;
                        }

                        if (result) {
                            this.imageEventText.updateBodyText(CURSED_RESULT);
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Writhe(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        } else {
                            this.imageEventText.updateBodyText(NORMAL_RESULT);
                        }

                        CardCrawlGame.sound.play("BLUNT_HEAVY");
                        CardCrawlGame.screenShake.rumble(2.0F);
                        AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
                        break;
                    default:
                        this.imageEventText.updateBodyText(NOPE_RESULT);
                }

                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[2]);
                this.screen = CurScreen.RESULT;
                break;
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
