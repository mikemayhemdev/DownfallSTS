package evilWithin.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.shrines.Lab;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.CultistPotion;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import evilWithin.relics.BrokenWingStatue;

public class TheNest_Evil extends AbstractImageEvent {
    public static final String ID = "evilWithin:Nest";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;

    private CUR_SCREEN screen;

    private AbstractCard dagger;

    public TheNest_Evil() {
        super(NAME, DESCRIPTIONS[0], "images/events/theNest.jpg");
        this.noCardsInRewards = true;
        AbstractDungeon.getCurrRoom().rewards.clear();
        if (AbstractDungeon.player.hasRelic(BrokenWingStatue.ID)){
            this.imageEventText.setDialogOption(OPTIONS[0]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[1], true);
        }
        this.imageEventText.setDialogOption(OPTIONS[3] + 6 + OPTIONS[4], new RitualDagger());
        this.imageEventText.setDialogOption(OPTIONS[2]);
        this.screen = CUR_SCREEN.INTRO;

        this.dagger = new RitualDagger();

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
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, 6, DamageInfo.DamageType.HP_LOSS));

                        CardCrawlGame.sound.play("ATTACK_DAGGER_6");
                        CardCrawlGame.sound.play("BLOOD_SPLAT");
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.dagger, (float)Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));
                        this.screen = CUR_SCREEN.COMPLETE;
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();

                        return;
                    case 2:

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.screen = CUR_SCREEN.COMPLETE;
                        AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getPotion(CultistPotion.POTION_ID)));
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        return;

                }
                break;
            case GIFTSTAGE1:
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.dagger, (float)Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));
                this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[2]);
                this.screen = CUR_SCREEN.GIFTSTAGE2;
                return;
            case GIFTSTAGE2:
                this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[8]);
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getPotion(CultistPotion.POTION_ID)));
                AbstractDungeon.combatRewardScreen.open();
                this.screen = CUR_SCREEN.GIFTSTAGE3;
                return;
            case GIFTSTAGE3:
                this.dagger.upgrade();
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(this.dagger.makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));

                this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[9]);
                this.screen = CUR_SCREEN.GIFTSTAGE4;
                return;
            case GIFTSTAGE4:
                this.dagger.misc += 5;
                this.dagger.applyPowers();
                this.dagger.baseDamage = this.dagger.misc;
                this.dagger.isDamageModified = false;
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(this.dagger.makeStatEquivalentCopy()));
                this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[5]);
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
