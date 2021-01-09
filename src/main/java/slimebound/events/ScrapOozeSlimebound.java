package slimebound.events;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import slimebound.relics.ScrapOozeRelic;

import java.util.ArrayList;
import java.util.Collections;

public class ScrapOozeSlimebound extends AbstractImageEvent {
    public static final String ID = "Slimebound:ScrapOoze";
    private static final EventStrings eventStrings;
    private static final EventStrings eventStringsAlt;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private int relicObtainChance = 25;
    private int dmg = 3;
    private int totalDamageDealt = 0;
    private int screenNum = 0;
    private static final String DIALOG_1;
    private static final String FAIL_MSG;
    private static final String SUCCESS_MSG;
    private static final String ESCAPE_MSG;

    private AbstractRelic relicOffered;

    public ScrapOozeSlimebound() {
        super(NAME, DIALOG_1, "images/events/scrapOoze.jpg");
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.dmg = 5;
        }

        this.imageEventText.setDialogOption(OPTIONS[0] + this.dmg + OPTIONS[1] + this.relicObtainChance + OPTIONS[2]);

        ArrayList<AbstractRelic> playerCommonRelics = new ArrayList<>();
        ArrayList<AbstractRelic> playerUncommonRelics = new ArrayList<>();

        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r.tier == AbstractRelic.RelicTier.COMMON) {
                playerCommonRelics.add(r);
            }
            if (r.tier == AbstractRelic.RelicTier.COMMON) {
                playerUncommonRelics.add(r);
            }
        }

        Collections.shuffle(playerCommonRelics);
        Collections.shuffle(playerUncommonRelics);


            if (playerCommonRelics.size() > 0) {
                this.relicOffered = playerCommonRelics.get(0);
            } else if (playerUncommonRelics.size() > 0) {
                this.relicOffered = playerUncommonRelics.get(0);
            }


        if (this.relicOffered != null) {
            this.imageEventText.setDialogOption(OPTIONSALT[0] + this.relicOffered.name + OPTIONSALT[1], new ScrapOozeRelic());
        } else {
            this.imageEventText.setDialogOption(OPTIONSALT[4], true);
        }


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
                        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, this.dmg));
                        CardCrawlGame.sound.play("ATTACK_POISON");
                        this.totalDamageDealt += this.dmg;
                        int random = AbstractDungeon.miscRng.random(0, 99);
                        if (random >= 99 - this.relicObtainChance) {
                            this.imageEventText.updateBodyText(SUCCESS_MSG);
                            AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                            AbstractEvent.logMetricObtainRelicAndDamage("Scrap Ooze", "Success", r, this.totalDamageDealt);
                            this.imageEventText.clearAllDialogs();
                            this.imageEventText.setDialogOption(OPTIONS[3]);
                            this.screenNum = 1;
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, r);
                        } else {
                            this.imageEventText.updateBodyText(FAIL_MSG);
                            this.relicObtainChance += 10;
                            ++this.dmg;
                            this.imageEventText.updateDialogOption(0, OPTIONS[4] + this.dmg + OPTIONS[1] + this.relicObtainChance + OPTIONS[2]);
                            this.imageEventText.updateDialogOption(1, OPTIONSALT[2], true);
                            this.imageEventText.updateDialogOption(2, OPTIONS[3]);
                        }

                        return;
                    case 1:
                        AbstractDungeon.player.loseRelic(this.relicOffered.relicId);
                        imageEventText.updateBodyText(DESCRIPTIONSALT[0]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, RelicLibrary.getRelic(ScrapOozeRelic.ID).makeCopy());
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.screenNum = 1;
                        return;
                    case 2:
                        AbstractEvent.logMetricTakeDamage("Scrap Ooze", "Fled", this.totalDamageDealt);
                        this.imageEventText.updateBodyText(ESCAPE_MSG);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
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
        eventStrings = CardCrawlGame.languagePack.getEventString("Scrap Ooze");
        eventStringsAlt = CardCrawlGame.languagePack.getEventString("Slimebound:ScrapOoze");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = eventStringsAlt.DESCRIPTIONS;
        OPTIONSALT = eventStringsAlt.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        FAIL_MSG = DESCRIPTIONS[1];
        SUCCESS_MSG = DESCRIPTIONS[2];
        ESCAPE_MSG = DESCRIPTIONS[3];
    }
}
