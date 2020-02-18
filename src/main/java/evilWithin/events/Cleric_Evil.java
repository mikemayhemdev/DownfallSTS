package evilWithin.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class Cleric_Evil extends AbstractImageEvent {
    public static final String ID = "evilWithin:Cleric";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESC;
    public static final String[] OPTIONS;
    public static final String[] OPTIONSALT;
    private CurrentScreen curScreen;
    private OptionChosen option;

    public Cleric_Evil() {
        super(NAME, DESC[0], "images/events/cleric.jpg");
        this.curScreen = CurrentScreen.INTRO;
        this.option = null;
        this.option = OptionChosen.NONE;
        if (AbstractDungeon.ascensionLevel >= 15) {
            //something here sometime
        }

        this.imageEventText.setDialogOption(OPTIONSALT[0]);
        this.imageEventText.setDialogOption(OPTIONSALT[1]);

    }


    public void update() {
        super.update();
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {// 65
            AbstractCard c = (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);// 66
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));// 67
            AbstractEvent.logMetricCardRemovalAtCost("The Cleric", "Card Removal", c, 0);// 68
            AbstractDungeon.player.masterDeck.removeCard(c);// 69
            AbstractDungeon.gridSelectScreen.selectedCards.remove(c);// 70
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.curScreen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESC[1]);
                        this.option = OptionChosen.PUNCH;
                        AbstractDungeon.effectList.add(new RainingGoldEffect(100));
                        AbstractDungeon.player.gainGold(100);
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESC[2]);
                        this.option = OptionChosen.THREATEN;
                        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[3], false, false, false, true);// 90 91 92
                        break;
                }

                this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                this.imageEventText.clearRemainingOptions();
                this.curScreen = CurrentScreen.DONE;
                break;
            case DONE:
            default:
                this.openMap();
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Designer");
        NAME = eventStrings.NAME;
        DESC = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
    }

    private static enum OptionChosen {
        PUNCH,
        THREATEN,
        NONE;

        private OptionChosen() {
        }
    }

    private static enum CurrentScreen {
        INTRO,
        DONE;

        private CurrentScreen() {
        }
    }
}
