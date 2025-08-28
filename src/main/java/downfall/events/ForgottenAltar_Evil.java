package downfall.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.cards.curses.Shame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class ForgottenAltar_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:ForgottenAltar";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Forgotten Altar");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
    }

    private int hpLoss;
    private int goldCost = 50;
    private CurScreen screen;

    public ForgottenAltar_Evil() {
        super(NAME, DESCRIPTIONS[0], "images/events/forgottenAltar.jpg");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.hpLoss = MathUtils.round((float) AbstractDungeon.player.maxHealth * 0.25F);
        } else {
            this.hpLoss = MathUtils.round((float) AbstractDungeon.player.maxHealth * 0.2F);
        }


        if (AbstractDungeon.ascensionLevel >= 15) {
            this.goldCost = 75;
        } else {
            this.goldCost = 50;
        }

        if (this.goldCost > AbstractDungeon.player.gold) {
            this.goldCost = AbstractDungeon.player.gold;
        }

        if (AbstractDungeon.player.gold >= goldCost) {
            this.imageEventText.setDialogOption(OPTIONSALT[0] + this.goldCost + OPTIONSALT[1] + (this.hpLoss) + OPTIONSALT[2]);
        } else {
            this.imageEventText.setDialogOption(OPTIONSALT[3] + this.goldCost + OPTIONSALT[4], true);
        }

        this.imageEventText.setDialogOption(OPTIONS[2] + 5 + OPTIONS[3] + this.hpLoss + OPTIONS[4]);

        if (15 >= AbstractDungeon.ascensionLevel) {
            this.imageEventText.setDialogOption(OPTIONSALT[5]);
        } else {
            this.imageEventText.setDialogOption(OPTIONSALT[6] + 5 + OPTIONSALT[7]);
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.clearAllDialogs();
                        AbstractDungeon.player.loseGold(this.goldCost);
                        AbstractDungeon.player.heal(this.hpLoss + 10, true);
                        this.imageEventText.updateBodyText(DESCRIPTIONSALT[0]);
                        this.imageEventText.setDialogOption(OPTIONSALT[5]);
                        CardCrawlGame.sound.play("HEAL_1");
                        this.screenNum = 1;
                        logMetricHealAtCost(ID, "Offer Souls", goldCost, hpLoss + 10);
                        return;
                    case 1:

                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.setDialogOption(OPTIONSALT[5]);
                        AbstractDungeon.player.increaseMaxHp(5, false);
                        AbstractDungeon.player.damage(new DamageInfo(null, this.hpLoss));
                        CardCrawlGame.sound.play("HEAL_3");
                        this.screenNum = 1;
                        logMetricDamageAndMaxHPGain(ID, "Shed Blood", this.hpLoss, 5);
                        return;
                    case 2:
                        this.imageEventText.clearAllDialogs();
                        if (15 > AbstractDungeon.ascensionLevel) {
                            this.imageEventText.updateBodyText(DESCRIPTIONSALT[1]);
                            this.imageEventText.setDialogOption(OPTIONSALT[5]);
                        } else {
                            //Shame curse = new Shame();
                            //AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, 5, DamageInfo.DamageType.HP_LOSS));
                            CardCrawlGame.sound.play("ATTACK_DAGGER_6");
                            CardCrawlGame.sound.play("BLOOD_SPLAT");
                            this.imageEventText.updateBodyText(DESCRIPTIONSALT[2]);
                            this.imageEventText.setDialogOption(OPTIONSALT[5]);
                        }
                        this.screenNum = 1;
                        logMetricIgnored(ID);
                        return;
                    default:
                        return;
                }
            default:
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
