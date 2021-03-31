package gremlin.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.cards.curses.Scatterbrained;

public class ScrapOozeGremlins extends AbstractImageEvent {
    public static final String ID = "Gremlin:ScrapOozeReplacement";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private int relicObtainChance = 25;
    private int curseObtainChance = 10;
    private int screenNum = 0;
    private static final String DIALOG_1;
    private static final String FAIL_MSG;
    private static final String SUCCESS_MSG;
    private static final String BIG_FAIL_MSG;
    private static final String ESCAPE_MSG;

    public ScrapOozeGremlins() {
        super(NAME, DIALOG_1, "images/events/scrapOoze.jpg");
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.curseObtainChance = 20;
        }

        this.imageEventText.setDialogOption(OPTIONS[0] + this.curseObtainChance + OPTIONS[1] + this.relicObtainChance + OPTIONS[2],
                new Scatterbrained());
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_OOZE");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screenNum) {
            case 0:
                switch(buttonPressed) {
                    case 0:
                        CardCrawlGame.sound.play("ATTACK_POISON");
                        int random = AbstractDungeon.miscRng.random(0, 99);
                        if (random < this.curseObtainChance) {
                            this.imageEventText.updateBodyText(BIG_FAIL_MSG);
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Scatterbrained(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                            this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                            this.imageEventText.removeDialogOption(1);
                            this.screenNum = 1;
                        } else if (random >= 99 - this.relicObtainChance) {
                            this.imageEventText.updateBodyText(SUCCESS_MSG);
                            AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                            this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                            this.imageEventText.removeDialogOption(1);
                            this.screenNum = 1;
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, r);
                        } else {
                            this.imageEventText.updateBodyText(FAIL_MSG);
                            this.curseObtainChance += 5;
                            this.relicObtainChance += 10;
                            if (this.curseObtainChance + this.relicObtainChance > 100) {
                                this.relicObtainChance = 100 - this.curseObtainChance;
                            }
                            this.imageEventText.updateDialogOption(0, OPTIONS[4] + this.curseObtainChance + OPTIONS[1] + this.relicObtainChance + OPTIONS[2], new Scatterbrained());
                            this.imageEventText.updateDialogOption(1, OPTIONS[3]);
                        }

                        return;
                    case 1:
                        this.imageEventText.updateBodyText(ESCAPE_MSG);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.removeDialogOption(1);
                        this.screenNum = 1;
                        return;
                    default:
                        return;
                }
            case 1:
                this.openMap();
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Gremlin:ScrapOozeReplacement");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        FAIL_MSG = DESCRIPTIONS[1];
        SUCCESS_MSG = DESCRIPTIONS[2];
        BIG_FAIL_MSG = DESCRIPTIONS[3];
        ESCAPE_MSG = DESCRIPTIONS[4];
    }
}
