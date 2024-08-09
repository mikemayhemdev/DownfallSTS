package theHexaghost.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.beyond.Falling;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import theHexaghost.HexaMod;

public class HexaFalling extends AbstractImageEvent {
    public static final String ID = HexaMod.makeID("HexaFalling");
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final EventStrings vanilla_eventStrings;

    private boolean attack;
    private boolean skill;
    private boolean power;
    private AbstractCard attackCard;
    private AbstractCard skillCard;
    private AbstractCard powerCard;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        vanilla_eventStrings = CardCrawlGame.languagePack.getEventString(Falling.ID);
        NAME = CardCrawlGame.languagePack.getEventString(Falling.ID).NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurScreen screen;

    public HexaFalling() {
        super(NAME, CardCrawlGame.languagePack.getEventString(Falling.ID).DESCRIPTIONS[0], "images/events/falling.jpg");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;
        this.setCards();
        this.imageEventText.setDialogOption(vanilla_eventStrings.OPTIONS[0]);
    }

    private void setCards() {

        this.attack = CardHelper.hasCardWithType(AbstractCard.CardType.ATTACK);
        this.skill = CardHelper.hasCardWithType(AbstractCard.CardType.SKILL);
        this.power = CardHelper.hasCardWithType(AbstractCard.CardType.POWER);

        if (this.attack) {
            this.attackCard = CardHelper.returnCardOfType(AbstractCard.CardType.ATTACK, AbstractDungeon.miscRng);
        }

        if (this.skill) {
            this.skillCard = CardHelper.returnCardOfType(AbstractCard.CardType.SKILL, AbstractDungeon.miscRng);
        }

        if (this.power) {
            this.powerCard = CardHelper.returnCardOfType(AbstractCard.CardType.POWER, AbstractDungeon.miscRng);
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.screen = HexaFalling.CurScreen.CHOICE;
                this.imageEventText.clearAllDialogs();

                if (!this.skill && !this.power && !this.attack) {
                    this.imageEventText.setDialogOption(OPTIONS[0]); // dont update the text if there's only the float option, so it feels like the old procedure
                } else {   // 4 options, the 4th one is appended after the vanilla 3
                    this.imageEventText.updateBodyText(vanilla_eventStrings.DESCRIPTIONS[1] + eventStrings.DESCRIPTIONS[1]);
                    if (this.skill) {
                        this.imageEventText.setDialogOption(vanilla_eventStrings.OPTIONS[1] + FontHelper.colorString(this.skillCard.name, "r"), this.skillCard.makeStatEquivalentCopy());
                    } else {
                        this.imageEventText.setDialogOption(vanilla_eventStrings.OPTIONS[2], true);
                    }

                    if (this.power) {
                        this.imageEventText.setDialogOption(vanilla_eventStrings.OPTIONS[3] + FontHelper.colorString(this.powerCard.name, "r"), this.powerCard.makeStatEquivalentCopy());
                    } else {
                        this.imageEventText.setDialogOption(vanilla_eventStrings.OPTIONS[4], true);
                    }

                    if (this.attack) {
                        this.imageEventText.setDialogOption(vanilla_eventStrings.OPTIONS[5] + FontHelper.colorString(this.attackCard.name, "r"), this.attackCard.makeStatEquivalentCopy());
                    } else {
                        this.imageEventText.setDialogOption(vanilla_eventStrings.OPTIONS[6], true);
                    }

                    this.imageEventText.setDialogOption(OPTIONS[0]);
                }



                break;

            case CHOICE:
                this.screen = HexaFalling.CurScreen.END;
                this.imageEventText.clearAllDialogs();
                this.imageEventText.updateBodyText(vanilla_eventStrings.DESCRIPTIONS[1]);

                switch(buttonPressed) {
                    case 0:
                        if (!this.skill && !this.power && !this.attack) {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[0]);
                            this.imageEventText.setDialogOption(OPTIONS[1]);
                            logMetric(HexaFalling.ID, "Float");
                        } else {
                            this.imageEventText.updateBodyText(vanilla_eventStrings.DESCRIPTIONS[2]);
                            AbstractDungeon.effectList.add(new PurgeCardEffect(this.skillCard));
                            AbstractDungeon.player.masterDeck.removeCard(this.skillCard);
                            this.imageEventText.setDialogOption(OPTIONS[1]);
                            logMetricCardRemoval(HexaFalling.ID, "Removed Skill", this.skillCard);
                        }

                        return;
                    case 1:
                        this.imageEventText.updateBodyText(vanilla_eventStrings.DESCRIPTIONS[3]);
                        AbstractDungeon.effectList.add(new PurgeCardEffect(this.powerCard));
                        AbstractDungeon.player.masterDeck.removeCard(this.powerCard);
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        logMetricCardRemoval(HexaFalling.ID, "Removed Power", this.powerCard);
                        return;
                    case 2:
                        this.imageEventText.updateBodyText(vanilla_eventStrings.DESCRIPTIONS[4]);
                        AbstractDungeon.effectList.add(new PurgeCardEffect(this.attackCard));
                        AbstractDungeon.player.masterDeck.removeCard(this.attackCard);
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        logMetricCardRemoval(HexaFalling.ID, "Removed Attack", this.attackCard);
                        return;
                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[0]);
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        logMetric(HexaFalling.ID, "Float");

                    default:
                        return;
                }

            case END:
                this.openMap();
        }

    }

    private enum CurScreen {
        INTRO,
        CHOICE,
        END;

        CurScreen() {
        }
    }
}
