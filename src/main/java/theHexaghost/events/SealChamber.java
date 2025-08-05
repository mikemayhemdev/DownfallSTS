package theHexaghost.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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
import theHexaghost.relics.TheBrokenSeal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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

    private HashMap<Integer, String> option_number_and_seals = new HashMap<>();

    public SealChamber() {
        super(NAME, DESCRIPTIONS[0], "hexamodResources/images/events/sealChamber.png");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;

        this.generate_options();
    }

    private void generate_options() {

        int option_number = 0;
        int number_of_options_to_add = 6;

        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<String> random_options = new ArrayList<>();
        for(int j = 1; j <= 6; j++){
            random_options.add( Integer.toString(j) );
        }

        for (AbstractCard c : p.masterDeck.group) {
            if (c instanceof FirstSeal) {
                number_of_options_to_add--;
                random_options.remove("1");
            } else if (c instanceof SecondSeal){
                number_of_options_to_add--;
                random_options.remove("2");
            } else if (c instanceof ThirdSeal){
                number_of_options_to_add--;
                random_options.remove("3");
            } else if (c instanceof FourthSeal){
                number_of_options_to_add--;
                random_options.remove("4");
            } else if (c instanceof SixthSeal){
                number_of_options_to_add--;
                random_options.remove("5");
            } else if (c instanceof FifthSeal){
                number_of_options_to_add--;
                random_options.remove("6");
            }
        }

        number_of_options_to_add = Math.min((AbstractDungeon.ascensionLevel >= 15)?2:3, number_of_options_to_add);
        Collections.shuffle(random_options, AbstractDungeon.eventRng.random);

        for(int i = 0; i < number_of_options_to_add; i++){
            String option = random_options.get(i);
            switch(option){
                case "1":
                    option_number_and_seals.put(option_number, "1");
                    option_number++;
                    this.imageEventText.setDialogOption(OPTIONS[0] + this.hpLoss + OPTIONS[1], new FirstSeal()); // no lock option because the player might have revive potion etc.
                    break;

                case "2":
                    option_number_and_seals.put(option_number, "2");
                    option_number++;
                    if (AbstractDungeon.player.gold >= this.goldLoss) {
                        this.imageEventText.setDialogOption(OPTIONS[0] + this.goldLoss + OPTIONS[2], new SecondSeal());
                    } else {
                        this.imageEventText.setDialogOption(OPTIONS[5], true);
                    }
                    break;

                case "3":
                    option_number_and_seals.put(option_number, "3");
                    option_number++;
                    this.cardOption = getRandomNonBasicCard();
                    if (this.cardOption != null) {
                        this.imageEventText.setDialogOption(OPTIONS[0] + this.cardOption.name + OPTIONS[3], new ThirdSeal());
                    } else {                               //need common
                        this.imageEventText.setDialogOption(OPTIONS[6], true);
                    }
                    break;

                case "4":
                    option_number_and_seals.put(option_number, "4");
                    option_number++;
                    this.potionOption = AbstractDungeon.player.getRandomPotion();
                    if (this.potionOption != null) {
                        this.imageEventText.setDialogOption(OPTIONS[0] + FontHelper.colorString(this.potionOption.name, "r") + OPTIONS[4], new FourthSeal());
                    } else {                               //need potion
                        this.imageEventText.setDialogOption(OPTIONS[7], true);
                    }
                    break;

                case "5":
                    option_number_and_seals.put(option_number, "5");
                    option_number++;
                    this.upgradedCardOption = getRandomUpgradedCard();
                    if (this.upgradedCardOption != null) {                                                    //5th seal
                        this.imageEventText.setDialogOption(OPTIONS[0] + this.upgradedCardOption.name + OPTIONS[10], new SixthSeal());
                    } else {                               //need upgraded
                        this.imageEventText.setDialogOption(OPTIONS[11], true);
                    }
                    break;

                case "6":
                    option_number_and_seals.put(option_number, "6");
                    option_number++;
                    if (AbstractDungeon.player.maxHealth > maxHpLoss) {                         //6th seal
                        this.imageEventText.setDialogOption(OPTIONS[0] + this.maxHpLoss + OPTIONS[12], new FifthSeal());
                    } else {                               //need maxhp
                        this.imageEventText.setDialogOption(OPTIONS[13], true);
                    }
                    break;
            }
        }

        option_number_and_seals.put(option_number, "Leave");
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


    private void proceed_event(int choice){
        String option = option_number_and_seals.get(choice);
        switch (option){
            case "1":
                AbstractDungeon.player.damage(new DamageInfo(null, this.hpLoss, DamageInfo.DamageType.HP_LOSS));
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FirstSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                damageTaken = hpLoss;
                cardsObtained.add(FirstSeal.ID);
                break;

            case "2":
                AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldLoss));
                AbstractDungeon.player.loseGold(this.goldLoss);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new SecondSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                cardsObtained.add(SecondSeal.ID);
                break;

            case "3":
                AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(this.cardOption, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.player.masterDeck.removeCard(this.cardOption);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new ThirdSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                cardsObtained.add(ThirdSeal.ID);
                cardsRemoved.add(this.cardOption.cardID);
                break;

            case "4":
                AbstractDungeon.player.removePotion(this.potionOption);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FourthSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                cardsObtained.add(FourthSeal.ID);
                break;

            case "5":
                AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(this.upgradedCardOption, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.player.masterDeck.removeCard(this.upgradedCardOption);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new SixthSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                cardsObtained.add(SixthSeal.ID);
                cardsRemoved.add(this.upgradedCardOption.cardID);
                break;

            case "6":
                AbstractDungeon.player.decreaseMaxHealth(this.maxHpLoss);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FifthSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                damageTaken = maxHpLoss;
                cardsObtained.add(FifthSeal.ID);
                break;

            case "Leave":
                this.imageEventText.clearAllDialogs();
                this.screen = CurScreen.END;
                this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                this.imageEventText.setDialogOption(OPTIONS[8]);
                logMetricIgnored(ID);
                return;
        }

        this.imageEventText.clearAllDialogs();
        this.screen = CurScreen.END;
        this.imageEventText.setDialogOption(OPTIONS[8]);
        this.imageEventText.updateBodyText(DESCRIPTIONS[1] + DESCRIPTIONS[3]);

        logMetric(ID, "Entered Chamber", cardsObtained, cardsRemoved, null, null,
                null, null, null,
                damageTaken, 0, 0, 0, 0, goldLost);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                proceed_event(buttonPressed);
                return;

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
