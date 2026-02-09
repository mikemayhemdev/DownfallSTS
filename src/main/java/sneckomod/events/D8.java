package sneckomod.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import sneckomod.SneckoMod;

import java.util.ArrayList;

import static sneckomod.util.ColorfulCardReward.TEXT;

public class D8 extends AbstractImageEvent {
    public static final String ID = "sneckomod:D8";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private final int finalDmg;
    private CurScreen screen;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    public D8() {
        super(NAME, DESCRIPTIONS[0], "sneckomodResources/images/events/D8.png");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;
        this.finalDmg = AbstractDungeon.ascensionLevel >= 15 ? 15 : 10;

        CardGroup overflowCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group) {
            if (c.hasTag(SneckoMod.OVERFLOW)) {
                overflowCards.addToTop(c);
            }
        }

        this.imageEventText.setDialogOption(OPTIONS[0] + this.finalDmg + OPTIONS[1]);
        if (overflowCards.size() > 0) {
            this.imageEventText.setDialogOption(OPTIONS[2], new Pain(), new sneckomod.relics.D8());
        } else {
            this.imageEventText.setDialogOption(OPTIONS[4], true);
        }
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.imageEventText.clearAllDialogs();
                switch (buttonPressed) {
                    case 0:
                        handleSpecialBonusOption();
                        break;
                    case 1:
                        handleTakeOption();
                        break;
                    case 2:
                        handleIgnoreOption();
                        break;
                }
                break;
            case END:
                this.openMap();
                break;
        }
    }

    private void handleSpecialBonusOption() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity == AbstractCard.CardRarity.RARE);
            if (newCard != null && !cardListDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard.makeCopy());
            }
        }
        AbstractDungeon.cardRewardScreen.open(cardsToReward, null, TEXT[2]);

        CardCrawlGame.sound.play("EVENT_TOME");
        this.imageEventText.clearAllDialogs();
        AbstractDungeon.player.damage(new DamageInfo(null, this.finalDmg, DamageInfo.DamageType.HP_LOSS));
        this.imageEventText.setDialogOption(OPTIONS[3]);
        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
        this.screen = CurScreen.END;
        logMetric(ID, "Shatter", null, null, null, null, null, null, null, finalDmg, 0, 0, 0, 0, 0);
    }

    private void handleTakeOption() {
        Pain curse = new Pain();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        sneckomod.relics.D8 relic = new sneckomod.relics.D8();
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), relic);

        this.imageEventText.clearAllDialogs();
        this.imageEventText.setDialogOption(OPTIONS[3]);
        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
        this.screen = CurScreen.END;
        logMetricObtainCardAndRelic(ID, "Take", curse, relic);
    }

    private void handleIgnoreOption() {
        this.imageEventText.clearAllDialogs();
        this.imageEventText.setDialogOption(OPTIONS[3]);
        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
        this.screen = CurScreen.END;
        logMetricIgnored(ID);
    }

    private boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID)) {
                return true;
            }
        }
        return false;
    }

    private enum CurScreen {
        INTRO,
        END;
    }
}
