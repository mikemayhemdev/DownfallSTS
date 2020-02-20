package guardian.events;

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
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import guardian.cards.DefendTwo;
import guardian.cards.Defend_Guardian;
import guardian.cards.StrikeTwo;
import guardian.cards.Strike_Guardian;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BackToBasicsGuardian extends AbstractImageEvent {
    public static final String ID = "Guardian:BackToBasics";
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
        eventStringsGuardian = CardCrawlGame.languagePack.getEventString("Guardian:BackToBasics");
        DESCRIPTIONSGUARDIAN = eventStringsGuardian.DESCRIPTIONS;
        OPTIONSGUARDIAN = eventStringsGuardian.OPTIONS;
    }

    private BackToBasicsGuardian.CUR_SCREEN screen;
    private List<String> cardsUpgraded;
    private ArrayList<AbstractCard> strikes;
    private ArrayList<AbstractCard> defends;
    private AbstractCard newStrike = null;
    private AbstractCard newDefend = null;

    public BackToBasicsGuardian() {
        super(NAME, DIALOG_1, "images/events/backToBasics.jpg");
        this.screen = BackToBasicsGuardian.CUR_SCREEN.INTRO;
        this.cardsUpgraded = new ArrayList();

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            c.update();
        }

        strikes = new ArrayList<>();
        defends = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.hasTag(BaseModCardTags.BASIC_STRIKE)) {
                strikes.add(c);
            }
            if (c.hasTag(BaseModCardTags.BASIC_DEFEND)) {
                defends.add(c);
            }
        }

        if (strikes.size() >= 2 && defends.size() >= 2) {
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

                    this.newStrike = new StrikeTwo();
                    this.newDefend = new DefendTwo();

                    if (this.strikes.get(0).upgraded || this.strikes.get(1).upgraded) newStrike.upgrade();
                    if (this.defends.get(0).upgraded || this.defends.get(1).upgraded) newDefend.upgrade();

                    AbstractDungeon.player.masterDeck.removeCard(this.strikes.get(0));
                    AbstractDungeon.player.masterDeck.removeCard(this.strikes.get(1));
                    AbstractDungeon.player.masterDeck.removeCard(this.defends.get(0));
                    AbstractDungeon.player.masterDeck.removeCard(this.defends.get(1));


                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(newStrike, (Settings.WIDTH * .3F), (float) (Settings.HEIGHT / 2)));
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(newDefend, (Settings.WIDTH * .7F), (float) (Settings.HEIGHT / 2)));


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

                this.screen = BackToBasicsGuardian.CUR_SCREEN.COMPLETE;
                break;
            case COMPLETE:
                this.openMap();
        }

    }

    private void upgradeStrikeAndDefends() {
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

        while (true) {
            AbstractCard c;
            do {
                if (!var1.hasNext()) {
                    return;
                }

                c = (AbstractCard) var1.next();
            } while (!(c instanceof Strike_Red) && !(c instanceof Defend_Red) && !(c instanceof Strike_Green) && !(c instanceof Defend_Green) && !(c instanceof Strike_Blue) && !(c instanceof Defend_Blue) && !(c instanceof Strike_Guardian) && !(c instanceof Defend_Guardian));

            if (c.canUpgrade()) {
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
