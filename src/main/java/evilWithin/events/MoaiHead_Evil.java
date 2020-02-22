package evilWithin.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.EventStrings;

public class MoaiHead_Evil extends AbstractImageEvent {
    public static final String ID = "evilWithin:MoaiHead";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] OPTIONSALT;
    private int hpAmt = 0;
    private final int goldAmount = 333;
    private int screenNum = 0;

    public MoaiHead_Evil() {
        super(NAME, DESCRIPTIONS[0], "images/events/moaiHead.jpg");
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.hpAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.18F);
        } else {
            this.hpAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.125F);
        }

        this.imageEventText.setDialogOption(OPTIONS[0] + this.hpAmt + OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONSALT[0] + this.goldAmount + OPTIONSALT[1] + this.hpAmt + OPTIONSALT[2]);

        this.imageEventText.setDialogOption(OPTIONS[4]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screenNum) {
            case 0:
                switch(buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        CardCrawlGame.screenShake.shake(ShakeIntensity.HIGH, ShakeDur.MED, true);
                        CardCrawlGame.sound.play("BLUNT_HEAVY");
                        AbstractDungeon.player.maxHealth -= this.hpAmt;
                        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
                            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
                        }

                        if (AbstractDungeon.player.maxHealth < 1) {
                            AbstractDungeon.player.maxHealth = 1;
                        }

                        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.screenNum = 1;
                        AbstractDungeon.player.loseGold(this.goldAmount);
                        AbstractDungeon.player.increaseMaxHp(this.hpAmt, false);
                        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    default:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                }
            default:
                this.openMap();
        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("The Moai Head");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
    }
}
