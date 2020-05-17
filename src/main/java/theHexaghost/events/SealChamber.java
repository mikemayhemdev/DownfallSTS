package theHexaghost.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.DarkstonePeriapt;
import com.megacrit.cardcrawl.relics.DuVuDoll;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theHexaghost.HexaMod;
import theHexaghost.cards.Haunted;
import theHexaghost.cards.seals.FirstSeal;
import theHexaghost.cards.seals.FourthSeal;
import theHexaghost.cards.seals.SecondSeal;
import theHexaghost.cards.seals.ThirdSeal;

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

    private int hpLoss = FirstSeal.MAGIC;
    private int goldLoss = SecondSeal.MAGIC;


    private AbstractPotion potionOption;
    private AbstractCard cardOption;

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
        } else {
            this.imageEventText.setDialogOption(OPTIONS[6], true);
        }

        this.potionOption = AbstractDungeon.player.getRandomPotion();
        if (this.potionOption != null) {
            this.imageEventText.setDialogOption(OPTIONS[0] + FontHelper.colorString(this.potionOption.name, "r") + OPTIONS[4], new FourthSeal());
        } else {
            this.imageEventText.setDialogOption(OPTIONS[7], true);
        }

        this.imageEventText.setDialogOption(OPTIONS[8]);

    }

    private AbstractCard getRandomNonBasicCard() {
        ArrayList<AbstractCard> list = new ArrayList();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if ((c.rarity == AbstractCard.CardRarity.COMMON)) {
                list.add(c);
            }
        }


        if (list.isEmpty()) {
            return null;
        }

        java.util.Collections.shuffle(list, new java.util.Random(AbstractDungeon.miscRng.randomLong()));
        return (AbstractCard) list.get(0);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.imageEventText.clearAllDialogs();
                switch (buttonPressed) {
                    case 0:
                        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature) null, this.hpLoss, DamageInfo.DamageType.HP_LOSS));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FirstSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[8]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.screen = CurScreen.END;
                        break;
                    case 1:
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldLoss));
                        AbstractDungeon.player.gainGold(this.goldLoss);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new SecondSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[8]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.screen = CurScreen.END;
                        return;
                    case 2:
                        AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(this.cardOption, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        AbstractDungeon.player.masterDeck.removeCard(this.cardOption);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new ThirdSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[8]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.screen = CurScreen.END;
                        return;
                    case 3:
                        AbstractDungeon.player.removePotion(this.potionOption);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FourthSeal(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[8]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.screen = CurScreen.END;
                        return;
                    case 4:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[8]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.screen = CurScreen.END;
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
