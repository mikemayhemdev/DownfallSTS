package timeeater.events;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PrayerWheel;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import timeeater.TimeEaterMod;
import timeeater.relics.SkipNextBossCardReward;
import timeeater.relics.SkipNextBossRelicReward;

import java.util.ArrayList;

public class FutureYoink extends AbstractImageEvent {
    public static final String ID = TimeEaterMod.makeID("FutureYoink");
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final Logger logger = LogManager.getLogger(com.megacrit.cardcrawl.events.city.DrugDealer.class.getName());
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private int screenNum = 0;
    ArrayList<AbstractCard> rares = new ArrayList<>();
    ArrayList<AbstractRelic> relics = new ArrayList<>();
    private boolean gotcards = false;
    private boolean gotrelics = false;
    private boolean canget2ndcard = false;
    private boolean gotcards2 = false;

    public FutureYoink() {
        super(NAME, DESCRIPTIONS[0], "images/events/drugDealer.jpg");
        AbstractDungeon.getCurrRoom().baseRareCardChance = 100;
        resetOptions();
    }

    public void resetOptions() {

        this.imageEventText.clearAllDialogs();
        this.imageEventText.setDialogOption(OPTIONS[0], gotcards);
        this.imageEventText.setDialogOption(OPTIONS[1], gotrelics);
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0: {
                switch (buttonPressed) {
                    case 0:
                        canget2ndcard = AbstractDungeon.player.hasRelic(PrayerWheel.ID);
                        rares = AbstractDungeon.getRewardCards();
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        for (AbstractCard c : rares) {
                            SlimeboundMod.logger.info("Adding rare to event: " + c.name);
                            this.imageEventText.setDialogOption(OPTIONS[3] + c.name + OPTIONS[4], c.makeCopy());
                        }
                        this.screenNum = 1;
                        break;
                    case 1:
                        for (int i = 0; i < 3; ++i) {
                            relics.add(AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS));
                        }
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3] + relics.get(0).name + OPTIONS[4], relics.get(0));
                        this.imageEventText.setDialogOption(OPTIONS[3] + relics.get(1).name + OPTIONS[4], relics.get(1));
                        this.imageEventText.setDialogOption(OPTIONS[3] + relics.get(2).name + OPTIONS[4], relics.get(2));

                        this.screenNum = 2;
                        break;
                    case 2:
                        if (gotrelics || gotcards) {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        }
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[2]);
                        this.screenNum = 3;
                        break;
                    default:
                        logger.info("ERROR: Unhandled case " + buttonPressed);
                }
                break;
            }
            case 1: {

                this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rares.get(buttonPressed).makeCopy(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                if (gotcards) gotcards2 = true;
                gotcards = true;

                if (canget2ndcard && !gotcards2) {
                    rares = AbstractDungeon.getRewardCards();
                    this.imageEventText.clearAllDialogs();
                    for (AbstractCard c : rares
                    ) {
                        this.imageEventText.setDialogOption(OPTIONS[3] + c.name + OPTIONS[4], c);
                    }
                    break;

                } else {
                    AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, 10));
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.FIRE));

                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new SkipNextBossCardReward());
                    resetOptions();
                    this.screenNum = 0;
                }

                break;

            }
            case 2: {

                this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, relics.get(buttonPressed));
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new SkipNextBossRelicReward());


                AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, 10));
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.FIRE));

                gotrelics = true;
                resetOptions();
                this.screenNum = 0;
                break;
            }

            case 3: {
                this.openMap();
                break;
            }
        }

    }

}
