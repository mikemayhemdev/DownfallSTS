package awakenedOne.events;


import awakenedOne.potions.CultistsDelight;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import downfall.events.GoldenShrine_Evil;
import downfall.relics.BrokenWingStatue;
import downfall.util.RareCardReward;

public class TheNestAwakened extends AbstractImageEvent {
    public static final String ID = "awakened:Nest";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private int hpAmt = 0;
    private int goldAmt = 0;

    private CUR_SCREEN screen;

    private AbstractCard dagger;

    public TheNestAwakened () {
        super(NAME, DESCRIPTIONS[0], "images/events/theNest.jpg");
        this.noCardsInRewards = true;
        AbstractDungeon.getCurrRoom().rewards.clear();
        if (AbstractDungeon.player.hasRelic(BrokenWingStatue.ID) || BrokenWingStatue.GIVEN){
            if ((AbstractDungeon.player.hasRelic(BrokenWingStatue.ID))) {
                this.imageEventText.setDialogOption(OPTIONS[0]);
            } else {
                this.imageEventText.setDialogOption(OPTIONS[10]);
            }
        } else {
            this.imageEventText.setDialogOption(OPTIONS[1], true);
        }

        if (AbstractDungeon.ascensionLevel >= 15) {
            this.hpAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.15F);
        } else {
            this.hpAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.10F);
        }

        this.imageEventText.setDialogOption(OPTIONS[3] + hpAmt + OPTIONS[4]);

        this.imageEventText.setDialogOption(OPTIONS[2]);

        if (AbstractDungeon.ascensionLevel >= 15) {
            this.goldAmt = 50;
        } else {
            this.goldAmt = 100;
        }
        this.imageEventText.setDialogOption(OPTIONS[5] + goldAmt + OPTIONS[6], new Doubt());


        this.screen = CUR_SCREEN.INTRO;

    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        AbstractDungeon.player.loseRelic(BrokenWingStatue.ID);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[7]);
                        this.screen = CUR_SCREEN.GIFTSTAGE1;
                        return;
                    case 1:
                        this.screen = CUR_SCREEN.COMPLETE;
                        logMetric(ID, "Took a rare card reward for " + hpAmt + " Max HP.");
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        CardCrawlGame.sound.play("ATTACK_DAGGER_6");
                        CardCrawlGame.sound.play("BLOOD_SPLAT");
                        AbstractDungeon.player.maxHealth -= this.hpAmt;

                        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
                            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
                        }

                        AbstractDungeon.getCurrRoom().rewards.add(new RareCardReward(AbstractDungeon.player.getCardColor()));
                        AbstractDungeon.combatRewardScreen.open();

                        this.imageEventText.updateDialogOption(0, OPTIONS[7]);
                        this.imageEventText.clearRemainingOptions();

                        return;
                    case 2:
                        logMetric(ID, "Took Potion");
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.screen = CUR_SCREEN.COMPLETE;
                        AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getPotion(CultistsDelight.POTION_ID)));
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();
                        this.imageEventText.updateDialogOption(0, OPTIONS[7]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 3:
                        logMetricGainGold(ID, "Donation Box", this.goldAmt);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[8]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[10]);

                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldAmt));
                        AbstractDungeon.player.gainGold(this.goldAmt);

                        AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.COMMON);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), r);
                        logMetricObtainRelic(ID, "Donation Box", r);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[10]);
                        this.imageEventText.clearRemainingOptions();
                        this.screen = CUR_SCREEN.GOLD2;
                        return;
                }
                break;

            case GOLD2:
                this.screen = CUR_SCREEN.COMPLETE;
                this.imageEventText.clearAllDialogs();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Doubt(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                this.imageEventText.updateBodyText(DESCRIPTIONS[9]);
                this.imageEventText.setDialogOption(OPTIONS[7]);
                this.imageEventText.clearRemainingOptions();
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                return;
            case GIFTSTAGE1:

                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getPotion(CultistsDelight.POTION_ID)));
                AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldAmt));
                AbstractDungeon.player.gainGold(this.goldAmt);
                AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.COMMON);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), r);

                logMetricGainGold(ID, "Returned Statue", this.goldAmt);
                logMetricObtainRelic(ID, "Returned Statue", r);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.dagger, (float)Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));
                this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[2]);
                this.screen = CUR_SCREEN.GIFTSTAGE4;
                return;
            case GIFTSTAGE4:
                this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[7]);
                this.screen = CUR_SCREEN.COMPLETE;
                return;


            default:
                this.openMap();
        }

    }

    private enum CUR_SCREEN {
        INTRO,
        GIFTSTAGE1,
        GIFTSTAGE2,
        GIFTSTAGE3,
        GIFTSTAGE4,
        GOLD2,
        COMPLETE;

        CUR_SCREEN() {
        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }
}
