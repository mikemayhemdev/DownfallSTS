package sneckomod.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.Collections;

public class BackToBasicsSnecko extends AbstractImageEvent {
    public static final String ID = "sneckomod:BackToBasics";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Back to Basics");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private final ArrayList<AbstractCard> cardsToRemove;
    private CUR_SCREEN screen = CUR_SCREEN.INTRO;

    public BackToBasicsSnecko() {
        super(NAME, DESCRIPTIONS[0], "images/events/backToBasics.jpg");
        cardsToRemove = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                cardsToRemove.add(c);
            }
        }

        if (!cardsToRemove.isEmpty()) {
            this.imageEventText.setDialogOption(OPTIONS[0]); // Replace Strikes and Defends
        } else {
            this.imageEventText.setDialogOption(OPTIONS[1], true); // No Strikes/Defends to replace
        }
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        if (this.screen == CUR_SCREEN.INTRO) {
            if (buttonPressed == 0 && !cardsToRemove.isEmpty()) {
                replaceStrikesAndDefendsWithRed();
                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
            }
            this.imageEventText.updateDialogOption(0, OPTIONS[2]); // Close
            this.imageEventText.clearRemainingOptions();
            this.screen = CUR_SCREEN.COMPLETE;
        } else {
            this.openMap();
        }
    }

    private void replaceStrikesAndDefendsWithRed() {
        ArrayList<String> cardsRemoved = new ArrayList<>();
        ArrayList<String> cardsAdded = new ArrayList<>();

        for (AbstractCard c : cardsToRemove) {
            AbstractCard replacement;
            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                replacement = new Strike_Red();
            } else {
                replacement = new Defend_Red();
            }

            if (c.upgraded) {
                replacement.upgrade();
            }

            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(replacement.makeStatEquivalentCopy(), Settings.WIDTH / 2F, Settings.HEIGHT / 2F));
            AbstractDungeon.player.masterDeck.removeCard(c);
            cardsRemoved.add(c.cardID);
            cardsAdded.add(replacement.cardID);
        }

        logMetricTransformCards(ID, "Replace with Red Strikes/Defends", cardsAdded, cardsRemoved);
    }

    private enum CUR_SCREEN {
        INTRO, COMPLETE
    }
}
