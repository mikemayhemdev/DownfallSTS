package slimebound.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.OddMushroom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.cards.Darklings;

import java.util.ArrayList;

public class DarklingsSlimebound extends AbstractEvent {
    private static final Logger logger = LogManager.getLogger(DarklingsSlimebound.class.getName());
    public static final String ID = "Slimebound:Darklings";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private int screenNum = 0;

    ArrayList<AbstractCard> validCards;
    public DarklingsSlimebound() {
        this.body = DESCRIPTIONS[0];// 43
        validCards = new ArrayList<>();
        for (AbstractCard c: AbstractDungeon.player.masterDeck.group){
            if (c.rarity == AbstractCard.CardRarity.RARE){
                validCards.add(c);
            }
        }

        this.roomEventText.addDialogOption(OPTIONS[1]);// 47

        if (validCards.size() > 0){
            this.roomEventText.addDialogOption(OPTIONS[0] + validCards.get(0).name + OPTIONS[3], CardLibrary.getCopy(Darklings.ID));// 45
        } else {
            this.roomEventText.addDialogOption(OPTIONS[4],true);// 45
        }

        AbstractDungeon.getCurrRoom().phase = RoomPhase.EVENT;// 48
        this.hasDialog = true;// 49
        this.hasFocus = true;// 50
    }// 51

    public void update() {
        super.update();// 54
        if (!RoomEventDialog.waitForInput) {// 55
            this.buttonEffect(this.roomEventText.getSelectedOption());// 56
        }

    }// 58

    protected void buttonEffect(int buttonPressed) {
        switch (buttonPressed) {// 62
            case 0:
                if (this.screenNum == 0) {// 65
                    AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("3 Darklings");// 66
                    this.roomEventText.updateBodyText(DESCRIPTIONS[1]);// 68
                    this.roomEventText.updateDialogOption(0, OPTIONS[2]);// 69
                    this.roomEventText.removeDialogOption(1);// 70
                    this.screenNum += 2;// 72
                } else if (this.screenNum == 1) {// 73
                    this.openMap();// 75
                } else if (this.screenNum == 2) {// 76
                    this.enterCombat();// 89
                }

                return;// 92
            case 1:
                AbstractCard bonus = new Darklings();// 956
                AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(

                        (AbstractCard) validCards.get(0), Settings.WIDTH * 0.35F, com.megacrit.cardcrawl.core.Settings.HEIGHT / 2));


                AbstractDungeon.player.masterDeck.removeCard((AbstractCard) validCards.get(0));

                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(bonus, (float) Settings.WIDTH * 0.65F, (float) Settings.HEIGHT / 2.0F));// 99
                this.roomEventText.updateBodyText(DESCRIPTIONS[2]);// 101
                this.roomEventText.updateDialogOption(0, OPTIONS[2]);// 102
                this.roomEventText.removeDialogOption(1);// 103
                this.screenNum = 1;// 104
                return;// 105
            default:
                logger.info("ERROR: case " + buttonPressed + " should never be called");// 107
        }
    }// 108

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);// 28
        NAME = eventStrings.NAME;// 29
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;// 30
        OPTIONS = eventStrings.OPTIONS;// 31
    }
}
