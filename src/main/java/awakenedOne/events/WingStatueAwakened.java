package awakenedOne.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.cards.curses.CurseOfBlood;
import downfall.relics.BrokenWingStatue;
import downfall.relics.ShatteredFragment;

public class WingStatueAwakened extends AbstractImageEvent {
    public static final String ID = "awakened:WingStatue";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private int damage;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurScreen screen;

    public WingStatueAwakened() {
        super(NAME, DESCRIPTIONS[0], "images/events/goldenWing.jpg");
        this.screen = CurScreen.INTRO;

        if (AbstractDungeon.ascensionLevel >= 15) {
            this.damage = (int)((float)AbstractDungeon.player.maxHealth * 0.35F);
        } else {
            this.damage = (int)((float)AbstractDungeon.player.maxHealth * 0.25F);
        }

        this.imageEventText.setDialogOption(OPTIONS [0], new ShatteredFragment());
        this.imageEventText.setDialogOption(OPTIONS[2] + this.damage + OPTIONS[4], new BrokenWingStatue());
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        AbstractCard curse = new CurseOfBlood();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) (Settings.WIDTH * .35F), (float) (Settings.HEIGHT / 2)));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) (Settings.WIDTH * .65F), (float) (Settings.HEIGHT / 2)));

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new ShatteredFragment());
                        this.screen = CurScreen.RESULT;
                        logMetricObtainCardAndRelic(ID, "Destroyed Statue", curse, new ShatteredFragment());
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        AbstractDungeon.player.damage(new DamageInfo(null, (this.damage)));
                        this.screen = CurScreen.RESULT;
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new BrokenWingStatue());
                        logMetricObtainRelicAndDamage(ID, "Collected Statue", new BrokenWingStatue(), ((AbstractDungeon.ascensionLevel >= 15)?7:5));
                        return;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.screen = CurScreen.RESULT;
                        logMetricIgnored(ID);
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
