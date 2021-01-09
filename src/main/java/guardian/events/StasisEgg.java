//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import guardian.GuardianMod;
import downfall.cards.curses.Aged;
import guardian.ui.RelicPreviewButton;

public class StasisEgg extends AbstractImageEvent {
    public static final String ID = "Guardian:StasisEgg";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String DIALOG_START;
    private static final String DIALOG_LEAVE;
    private static final String DIALOG_USE;
    private static final String DIALOG_SMASH;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_LEAVE = DESCRIPTIONS[3];
        DIALOG_START = DESCRIPTIONS[0];
        DIALOG_USE = DESCRIPTIONS[1];
        DIALOG_SMASH = DESCRIPTIONS[2];
    }

    private int screenNum = 0;
    private int maxHP = 0;

    public StasisEgg() {
        super(NAME, DIALOG_START, GuardianMod.getResourcePath("/events/stasisEgg.jpg"));
        this.imageEventText.updateBodyText(DIALOG_START);
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.maxHP = (int) ((float) AbstractDungeon.player.maxHealth * .1F);
        } else {
            this.maxHP = (int) ((float) AbstractDungeon.player.maxHealth * 0.15F);
        }
        imageEventText.optionList.add(new RelicPreviewButton(0, OPTIONS[0], new guardian.relics.StasisEgg(), false, new Aged()));
        this.imageEventText.setDialogOption(OPTIONS[1] + this.maxHP + OPTIONS[2], CardLibrary.getCopy(Pain.ID, 0, 0));
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_GOLDEN");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DIALOG_USE);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new guardian.relics.StasisEgg());
                        AbstractCard card = new Aged();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();

                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DIALOG_SMASH);
                        AbstractDungeon.player.increaseMaxHp(this.maxHP, false);
                        AbstractCard card2 = new Pain();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card2, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));


                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();

                        return;
                    case 2:
                        this.imageEventText.updateBodyText(DIALOG_LEAVE);

                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
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
