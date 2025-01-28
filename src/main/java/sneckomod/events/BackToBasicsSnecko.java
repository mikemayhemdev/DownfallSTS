package sneckomod.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.List;

public class BackToBasicsSnecko extends AbstractImageEvent {
    public static final String ID = "sneckomod:BackToBasics";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSGUARDIAN;
    public static final String[] OPTIONSGUARDIAN;
    private static final EventStrings eventStrings;
    private static final EventStrings eventStringsGuardian;
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;


    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Back to Basics");
        NAME = eventStrings.NAME;
        eventStringsGuardian = CardCrawlGame.languagePack.getEventString("sneckomod:BackToBasics");
        DESCRIPTIONSGUARDIAN = eventStringsGuardian.DESCRIPTIONS;
        OPTIONSGUARDIAN = eventStringsGuardian.OPTIONS;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
    }

    private CUR_SCREEN screen;
    private List<String> cardsUpgraded;
    private ArrayList<AbstractCard> strikesToRemove;
    private ArrayList<AbstractCard> defendsToRemove;

    public BackToBasicsSnecko() {
        super(NAME, DIALOG_1, "images/events/backToBasics.jpg");
        this.screen = CUR_SCREEN.INTRO;
        this.cardsUpgraded = new ArrayList<>();
        this.strikesToRemove = new ArrayList<>();
        this.defendsToRemove = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                strikesToRemove.add(c);
            }
            if (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
                defendsToRemove.add(c);
            }
        }

        if (strikesToRemove.size()+defendsToRemove.size() >= 1) {
            this.imageEventText.setDialogOption(OPTIONSGUARDIAN[0]);

        } else {
            this.imageEventText.setDialogOption(OPTIONSGUARDIAN[1], true);

        }


        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);


    }
    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_ANCIENT");
        }
        this.cardsUpgraded.clear();
    }

    public void update() {
        super.update();
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.effectList.add(new PurgeCardEffect(c));
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
            logMetricCardRemoval(ID, "Elegance", c);
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                if (buttonPressed == 0) {
                    ArrayList<String> cardsRemoved = new ArrayList<>();
                    ArrayList<String> cardsAdded = new ArrayList<>();

                    for (AbstractCard c : strikesToRemove) {
                        cardsRemoved.add(c.cardID);
                        AbstractCard newCard = new Strike_Red();
                        if (c.upgraded) {
                            newCard.upgrade();
                        }
                        cardsAdded.add(newCard.cardID);
                        AbstractDungeon.player.masterDeck.removeCard(c);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(newCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }

                    for (AbstractCard c : defendsToRemove) {
                        cardsRemoved.add(c.cardID);
                        AbstractCard newCard = new Defend_Red();
                        if (c.upgraded) {
                            newCard.upgrade();
                        }
                        cardsAdded.add(newCard.cardID);
                        AbstractDungeon.player.masterDeck.removeCard(c);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(newCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }

                    logMetric(ID, "Caprice", cardsAdded, cardsRemoved, null, null, null, null, null, 0, 0, 0, 0, 0, 0);

                    this.imageEventText.updateBodyText(DESCRIPTIONSGUARDIAN[0]);
                    this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                    this.imageEventText.clearRemainingOptions();
                } else if (buttonPressed == 1) {
                    if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                        this.imageEventText.updateBodyText(DIALOG_2);
                        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[2], false);
                    }

                    this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                    this.imageEventText.clearRemainingOptions();
                } else {
                    this.imageEventText.updateBodyText(DIALOG_3);
                    this.upgradeStrikeAndDefends();
                    this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                    this.imageEventText.clearRemainingOptions();
                }

                this.screen = CUR_SCREEN.COMPLETE;
                break;

            case COMPLETE:
                this.openMap();
        }
    }

    private void upgradeStrikeAndDefends() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade() && (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.hasTag(AbstractCard.CardTags.STARTER_STRIKE))) {
                c.upgrade();
                this.cardsUpgraded.add(c.cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck(c);
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT));
            }
        }
        logMetricUpgradeCards(ID, "Simplicity", cardsUpgraded);
    }

    private enum CUR_SCREEN {
        INTRO,
        COMPLETE
    }
}
