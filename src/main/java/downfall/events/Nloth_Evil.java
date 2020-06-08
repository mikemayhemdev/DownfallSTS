package downfall.events;


import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.NlothsGift;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Nloth_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:Nloth";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private static final EventStrings eventStrings;
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("N'loth");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
    }

    private int screenNum = 0;
    private AbstractRelic choice1;
    private AbstractRelic choice2;
    private AbstractRelic gift;

    public Nloth_Evil() {
        super(NAME, DIALOG_1, "images/events/nloth.jpg");
        ArrayList<AbstractRelic> relics = new ArrayList();
        relics.addAll(AbstractDungeon.player.relics);
        Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
        this.choice1 = relics.get(0);
        this.choice2 = relics.get(1);
        this.gift = new NlothsGift();
        this.imageEventText.setDialogOption(OPTIONS[0] + this.choice1.name + OPTIONS[1], new NlothsGift());
        this.imageEventText.setDialogOption(OPTIONS[0] + this.choice2.name + OPTIONS[1], new NlothsGift());
        this.imageEventText.setDialogOption(OPTIONSALT[0], new Pain());
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_SERPENT");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DIALOG_2);
                        if (AbstractDungeon.player.hasRelic("Nloth's Gift")) {
                            this.gift = new Circlet();
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), this.gift);
                        } else {
                            AbstractDungeon.player.loseRelic(this.choice1.relicId);
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), this.gift);
                        }

                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DIALOG_2);
                        if (AbstractDungeon.player.hasRelic("Nloth's Gift")) {
                            this.gift = new Circlet();
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), this.gift);
                        } else {
                            AbstractDungeon.player.loseRelic(this.choice2.relicId);
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), this.gift);
                        }

                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[0]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Pain(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(AbstractDungeon.returnRandomRelicTier()));
                        CardCrawlGame.sound.play("BLUNT_HEAVY");
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 3:
                        this.imageEventText.updateBodyText(DIALOG_3);
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    default:
                        this.imageEventText.updateBodyText(DIALOG_3);
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                }
            case 1:
                this.openMap();
                break;
            default:
                this.openMap();
        }

    }
}
