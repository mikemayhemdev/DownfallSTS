package sneckomod.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.cards.curses.Bewildered;

public class Serpent_Snecko extends AbstractImageEvent {
    public static final String ID = "sneckomod:Serpent";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String DIALOG_1;
    private static final String AGREE_DIALOG;
    private static final String DISAGREE_DIALOG;
    private static final int GOLD_REWARD = 175;
    private static final int A_2_GOLD_REWARD = 150;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        AGREE_DIALOG = DESCRIPTIONS[1];
        DISAGREE_DIALOG = DESCRIPTIONS[2];
    }

    private CUR_SCREEN screen;
    private int goldReward;
    private AbstractCard curse;

    public Serpent_Snecko() {
        super(NAME, DIALOG_1, "images/events/liarsGame.jpg");
        this.screen = CUR_SCREEN.INTRO;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.goldReward = 150;
        } else {
            this.goldReward = 175;
        }

        this.curse = new Bewildered();
        this.imageEventText.setDialogOption(OPTIONS[0], CardLibrary.getCopy(this.curse.cardID));
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_SERPENT");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                if (buttonPressed == 0) {
                    this.imageEventText.updateBodyText(AGREE_DIALOG);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.curse, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));

                    com.megacrit.cardcrawl.relics.AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, r);

                    this.screen = CUR_SCREEN.AGREE;
                } else {
                    this.imageEventText.updateBodyText(DISAGREE_DIALOG);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                    this.screen = CUR_SCREEN.DISAGREE;
                }
                break;
            default:
                this.openMap();
        }

    }

    private enum CUR_SCREEN {
        INTRO,
        AGREE,
        DISAGREE,
        COMPLETE;

        CUR_SCREEN() {
        }
    }
}
