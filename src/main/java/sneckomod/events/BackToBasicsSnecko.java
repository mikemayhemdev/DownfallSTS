package sneckomod.events;

import basemod.helpers.BaseModCardTags;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import sneckomod.SneckoMod;
import sneckomod.cards.Defend;
import sneckomod.cards.Strike;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import sneckomod.cards.unknowns.UnknownClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
        eventStringsGuardian = CardCrawlGame.languagePack.getEventString("sneckomod:BackToBasics");
        DESCRIPTIONSGUARDIAN = eventStringsGuardian.DESCRIPTIONS;
        OPTIONSGUARDIAN = eventStringsGuardian.OPTIONS;
    }

    private BackToBasicsSnecko.CUR_SCREEN screen;
    private List<String> cardsUpgraded;
    private ArrayList<AbstractCard> cardsToRemove;

    public BackToBasicsSnecko() {
        super(NAME, DIALOG_1, "images/events/backToBasics.jpg");
        this.screen = BackToBasicsSnecko.CUR_SCREEN.INTRO;
        this.cardsUpgraded = new ArrayList();

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            c.update();
        }

        cardsToRemove = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
                cardsToRemove.add(c);
            }
            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                cardsToRemove.add(c);
            }
        }

        if (cardsToRemove.size() >= 1) {
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
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                if (buttonPressed == 0) {
                    ArrayList<AbstractCard> list = new ArrayList<>();

                    for (AbstractCard c : CardLibrary.getAllCards()) {
                        if (c instanceof AbstractUnknownCard) {
                            if (c instanceof UnknownClass) {
                                UnknownClass cU = (UnknownClass) c;
                                if (SneckoMod.validColors.contains(cU.myColor)) {
                                    list.add(c);
                                }
                            } else {
                                list.add(c);
                            }
                        }
                    }

                    for (AbstractCard c : cardsToRemove) {
                        Collections.shuffle(list);
                        AbstractCard cU = list.get(0);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cU.makeStatEquivalentCopy(), (Settings.WIDTH / 2F), (float) (Settings.HEIGHT / 2)));
                    }

                    for (AbstractCard c : cardsToRemove) {
                        AbstractDungeon.player.masterDeck.removeCard(c);
                    }

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

                this.screen = BackToBasicsSnecko.CUR_SCREEN.COMPLETE;
                break;
            case COMPLETE:
                this.openMap();
        }

    }

    private void upgradeStrikeAndDefends() {
        for (AbstractCard c: AbstractDungeon.player.masterDeck.group){
            if (c.canUpgrade() && (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) ) {
                c.upgrade();
                this.cardsUpgraded.add(c.cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck(c);
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT));
            }
        }
    }

    private enum CUR_SCREEN {
        INTRO,
        COMPLETE;

        CUR_SCREEN() {
        }
    }
}
