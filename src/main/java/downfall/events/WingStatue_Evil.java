package downfall.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import downfall.relics.BrokenWingStatue;
import downfall.relics.ShatteredFragment;
import gremlin.patches.GremlinEnum;

public class WingStatue_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:WingStatue";
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
    private int goldLoss;

    public WingStatue_Evil() {
        super(NAME, DESCRIPTIONS[0], "images/events/goldenWing.jpg");
        this.screen = CurScreen.INTRO;



        this.goldLoss = MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.5F);
        if (AbstractDungeon.ascensionLevel >= 15){
            this.goldLoss = MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.25F);
        }

        if (AbstractDungeon.player.chosenClass == GremlinEnum.GREMLIN) {
            this.goldLoss = this.goldLoss*5;
        }

//        if (AbstractDungeon.player.chosenClass == GremlinEnum.GREMLIN) {
//        }
//        if (this.goldLoss >= AbstractDungeon.player.maxHealth) {
//            this.goldLoss = AbstractDungeon.player.maxHealth - 1;
//        }

//        if (AbstractDungeon.ascensionLevel >= 15) {
//            this.goldLoss = AbstractDungeon.miscRng.random(100, 125);
//        } else {
//            this.goldLoss = AbstractDungeon.miscRng.random(75, 100);
//        }

//        if (this.goldLoss > AbstractDungeon.player.gold) {
//            this.goldLoss = AbstractDungeon.player.gold;
//        }


        // lose 20 (25)% hp as damage, obtain relic
        this.imageEventText.setDialogOption(OPTIONS [0] + goldLoss + OPTIONS[1], new ShatteredFragment());
        //
        this.imageEventText.setDialogOption(OPTIONS[2] + ((AbstractDungeon.ascensionLevel >= 15)?7:5) + OPTIONS[4], new BrokenWingStatue());
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new ShatteredFragment());
//                        AbstractDungeon.player.decreaseMaxHealth(this.damage);
                      //  AbstractDungeon.player.damage(new DamageInfo(null, this.damage));
                       // AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AttackEffect.FIRE));
                        AbstractDungeon.player.damage(new DamageInfo(null, this.goldLoss));
                        this.screen = CurScreen.RESULT;
                        logMetricObtainRelicAndDamage(ID, "Destroyed Statue", new ShatteredFragment(), goldLoss);
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        AbstractDungeon.player.damage(new DamageInfo(null, ((AbstractDungeon.ascensionLevel >= 15)?7:5)));
                        this.screen = CurScreen.RESULT;
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new BrokenWingStatue());
                        logMetricObtainRelic(ID, "Collected Statue", new BrokenWingStatue());
                        return;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.screen = CurScreen.RESULT;
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
        RESULT;

        CurScreen() {
        }
    }
}
