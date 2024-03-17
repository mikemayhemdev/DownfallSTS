package theHexaghost.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theHexaghost.HexaMod;
import theHexaghost.cards.seals.*;

import java.util.ArrayList;

public class SealChamber extends AbstractImageEvent {
    public static final String ID = HexaMod.makeID("SealChamber");
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurScreen screen;

    private final int hpLoss = FirstSeal.MAGIC;
    private final int goldLoss = SecondSeal.MAGIC;
    private final int maxHpLoss = FifthSeal.MAGIC;

    private AbstractPotion potionOption;
    private AbstractCard cardOption;
    private AbstractCard upgradedCardOption;

//    private boolean used;

    public SealChamber() {
        super(NAME, DESCRIPTIONS[0], "hexamodResources/images/events/sealChamber.png");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;

        this.imageEventText.setDialogOption(OPTIONS[0] + this.hpLoss + OPTIONS[1], new FirstSeal());

        if (AbstractDungeon.player.gold >= this.goldLoss) {
            this.imageEventText.setDialogOption(OPTIONS[0] + this.goldLoss + OPTIONS[2], new SecondSeal());
        } else {
            this.imageEventText.setDialogOption(OPTIONS[5], true);
        }

        this.cardOption = getRandomNonBasicCard();
        if (this.cardOption != null) {
            this.imageEventText.setDialogOption(OPTIONS[0] + this.cardOption.name + OPTIONS[3], new ThirdSeal());
        } else {                               //need common
            this.imageEventText.setDialogOption(OPTIONS[6], true);
        }

        this.potionOption = AbstractDungeon.player.getRandomPotion();
        if (this.potionOption != null) {
            this.imageEventText.setDialogOption(OPTIONS[0] + FontHelper.colorString(this.potionOption.name, "r") + OPTIONS[4], new FourthSeal());
        } else {                               //need potion
            this.imageEventText.setDialogOption(OPTIONS[7], true);
        }

        this.upgradedCardOption = getRandomUpgradedCard();
        if (this.upgradedCardOption != null) {                                                    //5th seal
            this.imageEventText.setDialogOption(OPTIONS[0] + this.upgradedCardOption.name + OPTIONS[10], new SixthSeal());
        } else {                               //need upgraded
            this.imageEventText.setDialogOption(OPTIONS[11], true);
        }

        if (AbstractDungeon.player.maxHealth > maxHpLoss) {                         //6th seal
            this.imageEventText.setDialogOption(OPTIONS[0] + this.maxHpLoss + OPTIONS[12], new FifthSeal());
        } else {                               //need maxhp
            this.imageEventText.setDialogOption(OPTIONS[13], true);
        }

        this.imageEventText.setDialogOption(OPTIONS[8]);

    }

    private AbstractCard getRandomNonBasicCard() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if ((c.rarity == AbstractCard.CardRarity.COMMON)) {
                list.add(c);
            }
        }


        if (list.isEmpty()) {
            return null;
        }

        java.util.Collections.shuffle(list, new java.util.Random(AbstractDungeon.miscRng.randomLong()));
        return list.get(0);
    }

    private AbstractCard getRandomUpgradedCard() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.upgraded) {
                list.add(c);
            }
        }

        if (list.isEmpty()) {
            return null;
        }

        java.util.Collections.shuffle(list, new java.util.Random(AbstractDungeon.miscRng.randomLong()));
        return list.get(0);
    }

    private ArrayList<String> cardsRemoved = new ArrayList<>();
    private ArrayList<String> cardsObtained = new ArrayList<>();
    private int goldLost = 0;
    private int damageTaken = 0;


    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        AbstractDungeon.player.damage(new DamageInfo(null, this.hpLoss, DamageInfo.DamageType.HP_LOSS));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FirstSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        damageTaken = hpLoss;
                        cardsObtained.add(FirstSeal.ID);
//                        this.imageEventText.updateDialogOption(0, OPTIONS[9], true);
//                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
//                        this.screen = CurScreen.INTRO;
//                        used = true;

                        this.imageEventText.clearAllDialogs();
                        this.screen = CurScreen.END;
                        this.imageEventText.setDialogOption(OPTIONS[8]);
                        logMetric(ID, "Entered Chamber", cardsObtained, cardsRemoved, null, null,
                                null, null, null,
                                damageTaken, 0, 0, 0, 0, goldLost);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1] + DESCRIPTIONS[3]);

                        return;
                    case 1:
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldLoss));
                        AbstractDungeon.player.loseGold(this.goldLoss);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new SecondSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        goldLost = goldLoss;
                        cardsObtained.add(SecondSeal.ID);
//                        used = true;
//                        this.imageEventText.updateDialogOption(1, OPTIONS[9], true);
//
//                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
//                        this.screen = CurScreen.INTRO;

                        this.imageEventText.clearAllDialogs();
                        this.screen = CurScreen.END;
                        this.imageEventText.setDialogOption(OPTIONS[8]);
                        logMetric(ID, "Entered Chamber", cardsObtained, cardsRemoved, null, null,
                                null, null, null,
                                damageTaken, 0, 0, 0, 0, goldLost);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1] + DESCRIPTIONS[3]);

                        return;
                    case 2:
                        AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(this.cardOption, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        AbstractDungeon.player.masterDeck.removeCard(this.cardOption);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new ThirdSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        cardsObtained.add(ThirdSeal.ID);
                        cardsRemoved.add(this.cardOption.cardID);
//                        this.imageEventText.updateDialogOption(2, OPTIONS[9], true);
//
//                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
//                        this.screen = CurScreen.INTRO;
//                        used = true;
                        this.imageEventText.clearAllDialogs();
                        this.screen = CurScreen.END;
                        this.imageEventText.setDialogOption(OPTIONS[8]);
                        logMetric(ID, "Entered Chamber", cardsObtained, cardsRemoved, null, null,
                                null, null, null,
                                damageTaken, 0, 0, 0, 0, goldLost);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1] + DESCRIPTIONS[3]);

                        return;
                    case 3:
                        AbstractDungeon.player.removePotion(this.potionOption);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FourthSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        cardsObtained.add(FourthSeal.ID);
//                        this.imageEventText.updateDialogOption(3, OPTIONS[9], true);

//                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
//                        this.screen = CurScreen.INTRO;
//                        used = true;
                        this.imageEventText.clearAllDialogs();
                        this.screen = CurScreen.END;
                        this.imageEventText.setDialogOption(OPTIONS[8]);
                        logMetric(ID, "Entered Chamber", cardsObtained, cardsRemoved, null, null,
                                null, null, null,
                                damageTaken, 0, 0, 0, 0, goldLost);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1] + DESCRIPTIONS[3]);

                        return;

                    case 4:
                        AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(this.upgradedCardOption, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        AbstractDungeon.player.masterDeck.removeCard(this.upgradedCardOption);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new SixthSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        cardsObtained.add(SixthSeal.ID);
                        cardsRemoved.add(this.upgradedCardOption.cardID);
//                        this.imageEventText.updateDialogOption(2, OPTIONS[9], true);
//
//                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
//                        this.screen = CurScreen.INTRO;
//                        used = true;
                        this.imageEventText.clearAllDialogs();
                        this.screen = CurScreen.END;
                        this.imageEventText.setDialogOption(OPTIONS[8]);
                        logMetric(ID, "Entered Chamber", cardsObtained, cardsRemoved, null, null,
                                null, null, null,
                                damageTaken, 0, 0, 0, 0, goldLost);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1] + DESCRIPTIONS[3]);
                        return;

                    case 5:
                        AbstractDungeon.player.decreaseMaxHealth(this.maxHpLoss);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FifthSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        damageTaken = maxHpLoss;
                        cardsObtained.add(FifthSeal.ID);
//                        this.imageEventText.updateDialogOption(0, OPTIONS[9], true);
//                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
//                        this.screen = CurScreen.INTRO;
//                        used = true;

                        this.imageEventText.clearAllDialogs();
                        this.screen = CurScreen.END;
                        this.imageEventText.setDialogOption(OPTIONS[8]);
                        logMetric(ID, "Entered Chamber", cardsObtained, cardsRemoved, null, null,
                                null, null, null,
                                damageTaken, 0, 0, 0, 0, goldLost);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1] + DESCRIPTIONS[3]);

                        return;

                    case 6:
                        this.imageEventText.clearAllDialogs();
                        this.screen = CurScreen.END;
                        this.imageEventText.setDialogOption(OPTIONS[8]);
//                        if (!used) {
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        logMetricIgnored(ID);
//                        } else {
//                            logMetric(ID, "Entered Chamber", cardsObtained, cardsRemoved, null, null,
//                                    null, null, null,
//                                    damageTaken, 0, 0, 0, 0, goldLost);
//                            this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
//                        }
                        return;
                }
            case END:
                this.openMap();
        }

    }

    private enum CurScreen {
        INTRO,
        END;

        CurScreen() {
        }
    }
}
