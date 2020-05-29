package downfall.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import downfall.downfallMod;

import java.util.*;

public class Designer_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:Designer";
    public static final String NAME;
    public static final String[] DESC;
    public static final String[] OPTIONS;
    public static final String[] OPTIONSALT;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Designer");
        NAME = eventStrings.NAME;
        DESC = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
    }

    private CurrentScreen curScreen;
    private OptionChosen option;
    private boolean adjustmentUpgradesOne;
    private boolean cleanUpRemovesCards;
    private int hpLoss;


    public Designer_Evil() {
        super(NAME, DESC[0], (downfallMod.assetPath("images/events/designerThreatened.png")));
        this.curScreen = CurrentScreen.LOOTER;
        this.option = null;
        this.option = OptionChosen.NONE;
        this.adjustmentUpgradesOne = AbstractDungeon.miscRng.randomBoolean();
        this.cleanUpRemovesCards = AbstractDungeon.miscRng.randomBoolean();
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.hpLoss = 12;
        } else {
            this.hpLoss = 8;
        }

        this.imageEventText.setDialogOption(OPTIONS[4] + this.hpLoss + OPTIONS[5] + OPTIONSALT[6]);
        this.imageEventText.setDialogOption(OPTIONSALT[3]);

        int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_LOOTER_1A"));
        } else if (roll == 1) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_LOOTER_1B"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_LOOTER_1C"));
        }
    }

    public void update() {
        super.update();
        if (this.option != OptionChosen.NONE) {
            AbstractCard c;
            AbstractCard upgradeCard;
            switch (this.option) {
                case REMOVE:
                    if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                        CardCrawlGame.sound.play("CARD_EXHAUST");
                        upgradeCard = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(upgradeCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        AbstractDungeon.player.masterDeck.removeCard(upgradeCard);
                        AbstractDungeon.gridSelectScreen.selectedCards.clear();
                        this.option = OptionChosen.NONE;
                    }
                    break;
                case REMOVE_AND_UPGRADE:
                    if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                        AbstractCard removeCard = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                        CardCrawlGame.sound.play("CARD_EXHAUST");
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(removeCard, (float) Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH - 20.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F));
                        AbstractDungeon.player.masterDeck.removeCard(removeCard);
                        AbstractDungeon.gridSelectScreen.selectedCards.clear();
                        ArrayList<AbstractCard> upgradableCards = new ArrayList();
                        Iterator var9 = AbstractDungeon.player.masterDeck.group.iterator();

                        while (var9.hasNext()) {
                            c = (AbstractCard) var9.next();
                            if (c.canUpgrade()) {
                                upgradableCards.add(c);
                            }
                        }

                        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
                        if (!upgradableCards.isEmpty()) {
                            upgradeCard = upgradableCards.get(0);
                            upgradeCard.upgrade();
                            AbstractDungeon.player.bottledCardUpgradeCheck(upgradeCard);
                            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradeCard.makeStatEquivalentCopy()));
                            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        } else {
                        }

                        this.option = OptionChosen.NONE;
                    }
                    break;
                case TRANSFORM:
                    if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                        List<String> transCards = new ArrayList();
                        List<String> obtainedCards = new ArrayList();
                        AbstractCard newCard1;
                        if (AbstractDungeon.gridSelectScreen.selectedCards.size() == 2) {
                            c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                            AbstractDungeon.player.masterDeck.removeCard(c);
                            transCards.add(c.cardID);
                            AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);
                            newCard1 = AbstractDungeon.getTransformedCard();
                            obtainedCards.add(newCard1.cardID);
                            AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(newCard1, (float) Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F));
                            c = AbstractDungeon.gridSelectScreen.selectedCards.get(1);
                            AbstractDungeon.player.masterDeck.removeCard(c);
                            transCards.add(c.cardID);
                            AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);
                            c = AbstractDungeon.getTransformedCard();
                            obtainedCards.add(c.cardID);
                            AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 20.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.gridSelectScreen.selectedCards.clear();
                        } else {
                            c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                            AbstractDungeon.player.masterDeck.removeCard(c);
                            AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);
                            newCard1 = AbstractDungeon.getTransformedCard();
                            AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(newCard1, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.gridSelectScreen.selectedCards.clear();
                        }

                        this.option = OptionChosen.NONE;
                    }
                    break;
                case UPGRADE:
                    if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                        AbstractDungeon.gridSelectScreen.selectedCards.get(0).upgrade();
                        AbstractDungeon.player.bottledCardUpgradeCheck(AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                        AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0).makeStatEquivalentCopy()));
                        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        AbstractDungeon.gridSelectScreen.selectedCards.clear();
                        this.option = OptionChosen.NONE;
                    }
            }
        }

    }

    protected void buttonEffect(int buttonPressed) {


        switch (this.curScreen) {
            case LOOTER:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESC[1]);
                        AbstractDungeon.player.damage(new DamageInfo(null, this.hpLoss, DamageType.HP_LOSS));
                        int roll = MathUtils.random(2);
                        if (roll == 0) {
                            CardCrawlGame.sound.play("VO_LOOTER_2A");
                        } else if (roll == 1) {
                            CardCrawlGame.sound.play("VO_LOOTER_2B");
                        } else {
                            CardCrawlGame.sound.play("VO_LOOTER_2C");
                        }
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONSALT[5]);
                        this.curScreen = CurrentScreen.INTRO;
                        this.imageEventText.loadImage("images/events/designer2.jpg");
                        break;

                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(DESC[2]);
                        this.imageEventText.setDialogOption(OPTIONS[14]);
                        this.curScreen = CurrentScreen.DONE;
                        break;
                }
                break;
            case INTRO:
                this.imageEventText.updateBodyText(DESC[3]);
                this.imageEventText.removeDialogOption(0);
                if (this.adjustmentUpgradesOne) {
                    this.imageEventText.updateDialogOption(0, OPTIONSALT[0] + OPTIONS[9], !AbstractDungeon.player.masterDeck.hasUpgradableCards());
                } else {
                    this.imageEventText.updateDialogOption(0, OPTIONSALT[0] + OPTIONS[7] + 2 + OPTIONS[8], !AbstractDungeon.player.masterDeck.hasUpgradableCards());
                }

                if (this.cleanUpRemovesCards) {
                    this.imageEventText.setDialogOption(OPTIONSALT[1] + OPTIONS[10], CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).size() == 0);
                } else {
                    this.imageEventText.setDialogOption(OPTIONSALT[1] + OPTIONS[11] + 2 + OPTIONS[12], CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).size() < 2);
                }

                this.imageEventText.setDialogOption(OPTIONSALT[2] + OPTIONS[13], CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).size() == 0);
                this.imageEventText.setDialogOption(OPTIONSALT[4]);
                this.curScreen = CurrentScreen.MAIN;
                break;
            case MAIN:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESC[4]);
                        if (this.adjustmentUpgradesOne) {
                            this.option = OptionChosen.UPGRADE;
                            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, OPTIONS[15], true, false, false, false);
                        } else {
                            this.upgradeTwoRandomCards();
                        }
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESC[4]);
                        if (this.cleanUpRemovesCards) {
                            this.option = OptionChosen.REMOVE;
                            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[17], false, false, false, true);
                        } else {
                            this.option = OptionChosen.TRANSFORM;
                            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 2, OPTIONS[16], false, false, false, false);
                        }
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESC[4]);
                        this.option = OptionChosen.REMOVE_AND_UPGRADE;
                        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[17], false, false, false, true);
                        break;
                    case 3:
                        this.imageEventText.loadImage("images/events/designerPunched2.jpg");
                        this.imageEventText.updateBodyText(DESC[5]);
                        logMetricTakeDamage("Designer", "Punched", this.hpLoss);
                        CardCrawlGame.sound.play("BLUNT_FAST");
                }

                this.imageEventText.updateDialogOption(0, OPTIONS[14]);
                this.imageEventText.clearRemainingOptions();
                this.curScreen = CurrentScreen.DONE;
                break;
            case DONE:
            default:
                this.openMap();
        }

    }

    private void upgradeTwoRandomCards() {
        ArrayList<AbstractCard> upgradableCards = new ArrayList();
        Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();

        while (var2.hasNext()) {
            AbstractCard c = (AbstractCard) var2.next();
            if (c.canUpgrade()) {
                upgradableCards.add(c);
            }
        }

        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if (upgradableCards.isEmpty()) {
        } else if (upgradableCards.size() == 1) {
            upgradableCards.get(0).upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        } else {
            List<String> cards = new ArrayList();
            cards.add(upgradableCards.get(0).cardID);
            cards.add(upgradableCards.get(1).cardID);
            upgradableCards.get(0).upgrade();
            upgradableCards.get(1).upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
            AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(1));
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy(), (float) Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradableCards.get(1).makeStatEquivalentCopy(), (float) Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 20.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        }

    }

    private enum OptionChosen {
        UPGRADE,
        REMOVE,
        REMOVE_AND_UPGRADE,
        TRANSFORM,
        NONE;

        OptionChosen() {
        }
    }

    private enum CurrentScreen {
        LOOTER,
        INTRO,
        MAIN,
        DONE;

        CurrentScreen() {
        }
    }
}
