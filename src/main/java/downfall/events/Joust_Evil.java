package downfall.events;


import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.city.TheJoust;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

public class Joust_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:Joust";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private static final EventStrings eventStrings;
    private float joustTimer;
    private int clangCount;
    private CurScreen screen;

    public void update() {
        super.update();// 48
        if (this.joustTimer != 0.0F) {// 49
            this.joustTimer -= Gdx.graphics.getDeltaTime();// 50
            if (this.joustTimer < 0.0F) {// 51
                ++this.clangCount;// 52
                if (this.clangCount == 1) {// 53
                    CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.MED, false);// 54
                    CardCrawlGame.sound.play("BLUNT_HEAVY");// 55
                    this.joustTimer = 1.0F;// 56
                } else if (this.clangCount == 2) {// 57
                    CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);// 58
                    CardCrawlGame.sound.play("BLUNT_FAST");// 59
                    this.joustTimer = 0.25F;// 60
                } else if (this.clangCount == 3) {// 61
                    CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.LONG, false);// 62
                    CardCrawlGame.sound.play("BLUNT_HEAVY");// 63
                    this.joustTimer = 0.0F;// 64
                }
            }
        }

    }// 68

    public Joust_Evil() {
        super(NAME, DESCRIPTIONSALT[0], "images/events/joust.jpg");
        int x = (int) (AbstractDungeon.player.maxHealth * 0.3);
        int u = (int) (AbstractDungeon.player.maxHealth * 0.15);
        this.imageEventText.setDialogOption(OPTIONSALT[0] + x + OPTIONSALT[1] + OPTIONSALT[2]);
        this.imageEventText.setDialogOption(OPTIONSALT[3] + u + OPTIONSALT[1] + OPTIONSALT[4]);
        this.imageEventText.setDialogOption(OPTIONS[7]);
        this.screen = CurScreen.INTRO;
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {// 72
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.joustTimer = 0.01F;// 97
                        if (AbstractDungeon.cardRandomRng.randomBoolean()) {
                            AbstractDungeon.player.damage(new DamageInfo(null, (int) (AbstractDungeon.player.maxHealth * 0.3), DamageInfo.DamageType.HP_LOSS));
                            imageEventText.updateBodyText(DESCRIPTIONSALT[1]);
                        } else {
                            AbstractDungeon.effectList.add(new RainingGoldEffect(200));
                            AbstractDungeon.player.gainGold(200);
                            imageEventText.updateBodyText(DESCRIPTIONSALT[2]);
                        }
                    case 1:
                        this.joustTimer = 0.01F;// 97
                        int x = AbstractDungeon.cardRandomRng.random(100);
                        if (x < 25) {
                            AbstractDungeon.player.damage(new DamageInfo(null, (int) (AbstractDungeon.player.maxHealth * 0.15), DamageInfo.DamageType.HP_LOSS));
                            imageEventText.updateBodyText(DESCRIPTIONSALT[3]);
                        } else {
                            AbstractDungeon.effectList.add(new RainingGoldEffect(100));
                            AbstractDungeon.player.gainGold(100);
                            imageEventText.updateBodyText(DESCRIPTIONSALT[4]);
                        }
                    default:
                        this.imageEventText.updateDialogOption(0, OPTIONS[7]);// 75
                        this.imageEventText.clearRemainingOptions();// 76
                        this.openMap();// 77
                }
                this.imageEventText.updateDialogOption(0, OPTIONS[7]);
                this.imageEventText.clearRemainingOptions();
                this.screen = CurScreen.DONE;
                break;
            case DONE:
            default:
                this.openMap();
        }
    }// 135

    private enum CurScreen {
        INTRO,
        DONE;

        CurScreen() {
        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(TheJoust.ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
    }

}
