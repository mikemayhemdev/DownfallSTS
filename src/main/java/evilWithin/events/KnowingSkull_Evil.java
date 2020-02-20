package evilWithin.events;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import evilWithin.relics.KnowingSkull;

public class KnowingSkull_Evil extends AbstractImageEvent {
    public static final String ID = "evilWithin:KnowingSkull";
    public static final String NAME;
    public static final String[] DESC;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESC = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurrentScreen curScreen;
    private int takeCost;

    public KnowingSkull_Evil() {
        super(NAME, DESC[0], "images/events/knowingSkull.jpg");
        this.curScreen = CurrentScreen.INTRO;

        takeCost = AbstractDungeon.player.getAscensionMaxHPLoss();
        if (AbstractDungeon.ascensionLevel >= 15) {
            takeCost *= 2;
        }

        this.imageEventText.setDialogOption(OPTIONS[0] + takeCost + OPTIONS[2]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.curScreen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESC[1]);
                        AbstractDungeon.player.damage(new DamageInfo(null, takeCost, DamageInfo.DamageType.HP_LOSS));// 114
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, new KnowingSkull());
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESC[2]);
                        break;
                }

                this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                this.imageEventText.clearRemainingOptions();
                this.curScreen = CurrentScreen.DONE;
                break;
            case DONE:
            default:
                this.openMap();
        }

    }

    private enum CurrentScreen {
        INTRO,
        DONE;

        CurrentScreen() {
        }
    }
}
