package evilWithin.events;


import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import evilWithin.relics.BrokenWingStatue;
import evilWithin.relics.ShatteredFragment;

public class GoldenIdol_Evil extends AbstractImageEvent {
    public static final String ID = "evilWithin:GoldenIdol";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private CurScreen screen;
    private boolean trapAlreadySet = true;
    private int gold = 100;
    private AbstractCard strike = null;

    public GoldenIdol_Evil() {
        super(NAME, "", "images/events/goldenIdol.jpg");
        this.screen = CurScreen.INTRO;

        if (!this.trapAlreadySet){
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
                if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)){
                    this.strike = c;
                    this.imageEventText.setDialogOption(OPTIONS[0]);
                    break;
                }
            }
            if (this.strike == null){
                this.imageEventText.setDialogOption(OPTIONS[4], true);
            }
            this.body = DESCRIPTIONS[0];

        } else {
            this.body = DESCRIPTIONS[1];

            this.imageEventText.setDialogOption(OPTIONS[1] + this.gold + OPTIONS[2]);
        }
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
            case INTRO:
                switch(buttonPressed) {
                    case 0:
                        if (!this.trapAlreadySet){
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(this.strike, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                            AbstractDungeon.player.masterDeck.removeCard(strike);

                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                            AbstractDungeon.effectList.add(new RainingGoldEffect(this.gold));
                            AbstractDungeon.player.gainGold(this.gold);
                        }

                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.screen = CurScreen.RESULT;
                        return;
                    case 1:

                        if (!this.trapAlreadySet){
                            this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        }

                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.screen = CurScreen.RESULT;
                        return;
                    default:
                        return;
                }
            default:
                this.openMap();
        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private static enum CurScreen {
        INTRO,
        RESULT;

        private CurScreen() {
        }
    }
}
