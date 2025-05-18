package awakenedOne.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.WarpedTongs;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import downfall.cards.curses.Aged;
import downfall.cards.curses.Haunted;
import downfall.relics.BrokenWingStatue;
import downfall.relics.ShatteredFragment;
import gremlin.patches.GremlinEnum;

public class WingStatueAwakened extends AbstractImageEvent {
    public static final String ID = "awakened:WingStatue";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private int damage;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurScreen screen;
    private int goldLoss;

    public WingStatueAwakened() {
        super(NAME, DESCRIPTIONS[0], "images/events/goldenWing.jpg");
        this.screen = CurScreen.INTRO;

        this.goldLoss = MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.25F);
        if (AbstractDungeon.ascensionLevel >= 15){
            this.goldLoss = MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.35F);
        }
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.damage = (int)((float)AbstractDungeon.player.maxHealth * 0.35F);
        } else {
            this.damage = (int)((float)AbstractDungeon.player.maxHealth * 0.25F);
        }

        this.imageEventText.setDialogOption(OPTIONS [0], new ShatteredFragment());
        this.imageEventText.setDialogOption(OPTIONS[2] + this.damage + OPTIONS[4], new BrokenWingStatue());
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        AbstractCard curse = new Injury();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) (Settings.WIDTH * .35F), (float) (Settings.HEIGHT / 2)));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) (Settings.WIDTH * .65F), (float) (Settings.HEIGHT / 2)));

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new ShatteredFragment());
//                        AbstractDungeon.player.decreaseMaxHealth(this.damage);
                        //  AbstractDungeon.player.damage(new DamageInfo(null, this.damage));
                        // AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AttackEffect.FIRE));
                        AbstractDungeon.player.damage(new DamageInfo(null, this.goldLoss));
                        this.screen = CurScreen.RESULT;
                        logMetricObtainCardAndRelic(ID, "Destroyed Statue", curse, new ShatteredFragment());
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        AbstractDungeon.player.damage(new DamageInfo(null, ((AbstractDungeon.ascensionLevel >= 15)?7:5)));
                        this.screen = CurScreen.RESULT;
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new BrokenWingStatue());
                        logMetricObtainRelicAndDamage(ID, "Collected Statue", new BrokenWingStatue(), ((AbstractDungeon.ascensionLevel >= 15)?7:5));
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
