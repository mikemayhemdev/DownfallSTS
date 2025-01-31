package downfall.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import downfall.cards.curses.Icky;
import downfall.cards.curses.Malfunctioning;

import java.util.*;

public class ShiningLight_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:ShiningLight";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String INTRO;
    private static final String AGREE_DIALOG;
    private static final String DISAGREE_DIALOG;
    private int curseObtainChance = 3;
    private static final float HP_LOSS_PERCENT = 0.2F;
    private static final float A_2_HP_LOSS_PERCENT = 0.3F;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO = DESCRIPTIONS[0];
        AGREE_DIALOG = DESCRIPTIONS[1];
        DISAGREE_DIALOG = DESCRIPTIONS[2];
    }

    private int damage = 0;
    private CUR_SCREEN screen;

    public ShiningLight_Evil() {
        super(NAME, INTRO, "images/events/shiningLight.jpg");
        this.screen = CUR_SCREEN.INTRO;

        if (AbstractDungeon.player.masterDeck.hasUpgradableCards()) {
            if (AbstractDungeon.ascensionLevel >= 15) {
                this.curseObtainChance = 2;
            }
            this.imageEventText.setDialogOption(OPTIONS[0] + this.curseObtainChance + OPTIONS[3], new Malfunctioning());
        } else {
            this.imageEventText.setDialogOption(OPTIONS[2], true);
        }

        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_SHINING");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                if (buttonPressed == 0) {
                    this.imageEventText.updateBodyText(AGREE_DIALOG);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                    this.screen = CUR_SCREEN.COMPLETE;

                    this.upgradeCards();
                } else {
                    this.imageEventText.updateBodyText(DISAGREE_DIALOG);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                    this.screen = CUR_SCREEN.COMPLETE;
                    logMetricIgnored(ID);
                }
                break;
            default:
                this.openMap();
        }

    }

    private void upgradeCards() {
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        ArrayList<AbstractCard> upgradableCards = new ArrayList();
        Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();

        while (var2.hasNext()) {
            AbstractCard c = (AbstractCard) var2.next();
            if (c.canUpgrade()) {
                upgradableCards.add(c);
            }
        }

        List<String> cardMetrics = new ArrayList();
        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if (!upgradableCards.isEmpty()) {
            if (upgradableCards.size() == 1) {
                upgradableCards.get(0).upgrade();
                cardMetrics.add(upgradableCards.get(0).cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy()));
            } else if (upgradableCards.size() == 2) {
                upgradableCards.get(0).upgrade();
                upgradableCards.get(1).upgrade();
                cardMetrics.add(upgradableCards.get(0).cardID);
                cardMetrics.add(upgradableCards.get(1).cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(1));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy(), (float) Settings.WIDTH / 2.0F - 190.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(1).makeStatEquivalentCopy(), (float) Settings.WIDTH / 2.0F + 190.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F));
            } else {
                upgradableCards.get(0).upgrade();
                upgradableCards.get(1).upgrade();
                if (AbstractDungeon.ascensionLevel < 15) {
                    upgradableCards.get(2).upgrade();
                }
                cardMetrics.add(upgradableCards.get(0).cardID);
                cardMetrics.add(upgradableCards.get(1).cardID);
                if (AbstractDungeon.ascensionLevel < 15) {
                    cardMetrics.add(upgradableCards.get(2).cardID);
                }
                    AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(1));
                if (AbstractDungeon.ascensionLevel < 15) {
                    AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(2));
                }
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy(), (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(1).makeStatEquivalentCopy(), (float) Settings.WIDTH / 2.0F + 300.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F));
                if (AbstractDungeon.ascensionLevel < 15) {
                    AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(2).makeStatEquivalentCopy(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));}
            }
        }

        Malfunctioning curse = new Malfunctioning();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) Settings.WIDTH * .5F + 10.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F));
        logMetric(ID, "Entered Light", Collections.singletonList(curse.cardID), null, null, cardMetrics, null, null, null,
                0, 0, 0, 0, 0, 0);

    }

    private enum CUR_SCREEN {
        INTRO,
        COMPLETE;

        CUR_SCREEN() {
        }
    }
}

