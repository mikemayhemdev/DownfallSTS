package slimebound.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.cards.Darklings;

import java.util.ArrayList;
import java.util.Collections;

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
        this.body = DESCRIPTIONS[0];
        validCards = new ArrayList<>();
        for (AbstractCard c: AbstractDungeon.player.masterDeck.group){
            if (c.rarity == AbstractCard.CardRarity.RARE){
                validCards.add(c);
            }
        }

        this.roomEventText.addDialogOption(OPTIONS[1]);

        if (validCards.size() > 0){
            this.roomEventText.addDialogOption(OPTIONS[0] + validCards.get(0).name + OPTIONS[3], CardLibrary.getCopy(Darklings.ID));// 45
        } else {
            this.roomEventText.addDialogOption(OPTIONS[4],true);
        }

        AbstractDungeon.getCurrRoom().phase = RoomPhase.EVENT;
        this.hasDialog = true;
        this.hasFocus = true;
    }

    public void update() {
        super.update();
        if (!RoomEventDialog.waitForInput) {
            this.buttonEffect(this.roomEventText.getSelectedOption());
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (buttonPressed) {
            case 0:
                if (this.screenNum == 0) {
                    logMetric(ID, "Fight");
                    AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("3 Darklings");
                    this.roomEventText.updateBodyText(DESCRIPTIONS[1]);
                    this.roomEventText.updateDialogOption(0, OPTIONS[2]);
                    this.roomEventText.removeDialogOption(1);
                    this.screenNum += 2;
                } else if (this.screenNum == 1) {
                    this.openMap();
                } else if (this.screenNum == 2) {
                    this.enterCombat();
                }

                return;// 92
            case 1:
                AbstractCard bonus = new Darklings();
                AbstractCard cardRemoved = validCards.get(0);
                AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(
                        cardRemoved, Settings.WIDTH * 0.35F, com.megacrit.cardcrawl.core.Settings.HEIGHT / 2));


                AbstractDungeon.player.masterDeck.removeCard(cardRemoved);
                logMetric(ID, "Recruit", Collections.singletonList(bonus.cardID), Collections.singletonList(cardRemoved.cardID), null, null,
                        null, null, null,
                        0, 0, 0, 0, 0, 0);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(bonus, (float) Settings.WIDTH * 0.65F, (float) Settings.HEIGHT / 2.0F));// 99
                this.roomEventText.updateBodyText(DESCRIPTIONS[2]);
                this.roomEventText.updateDialogOption(0, OPTIONS[2]);
                this.roomEventText.removeDialogOption(1);
                this.screenNum = 1;
                return;
            default:
                logger.info("ERROR: case " + buttonPressed + " should never be called");
        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }
}
