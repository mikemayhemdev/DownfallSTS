package champ.events;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.ChampionsBelt;
import com.megacrit.cardcrawl.relics.CloakClasp;
import com.megacrit.cardcrawl.relics.WristBlade;
import downfall.events.CombatEvent;

public class MinorLeagueArena extends AbstractImageEvent implements CombatEvent {
    public static final String ID = "champ:MinorLeagueArena";
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

    public MinorLeagueArena() {
        super(NAME, DESCRIPTIONS[0], "champResources/images/events/minorarena.png");

        this.screen = CurScreen.INTRO;
        this.imageEventText.clearAllDialogs();
        this.imageEventText.setDialogOption(OPTIONS[0], new CloakClasp());
        this.imageEventText.setDialogOption(OPTIONS[1], new ChampionsBelt());
        this.imageEventText.setDialogOption(OPTIONS[2], new WristBlade());
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    protected void buttonEffect(int buttonPressed) {
        if (this.screen == CurScreen.INTRO) {
            switch (buttonPressed) {
                case 0:
                    logMetric(ID, "Fight");
                    this.screen = CurScreen.FIGHT;
                    //SlimeboundMod.logger.info("fight");
                    AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Centurion and Healer");
                    AbstractDungeon.getCurrRoom().rewards.clear();
                    AbstractDungeon.getCurrRoom().addRelicToRewards(new CloakClasp());
                    AbstractDungeon.getCurrRoom().addGoldToRewards(100);
                    AbstractDungeon.getCurrRoom().eliteTrigger = true;
                    AbstractDungeon.lastCombatMetricKey = "Centurion and Healer";
                    this.imageEventText.clearAllDialogs();
                    this.enterCombatFromImage();

                    return;
                case 1:
                    logMetric(ID, "Fight");

                    this.screen = CurScreen.FIGHT;
                    //SlimeboundMod.logger.info("fight");
                    AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Gremlin Nob");
                    AbstractDungeon.getCurrRoom().rewards.clear();
                    AbstractDungeon.getCurrRoom().addRelicToRewards(new ChampionsBelt());
                    AbstractDungeon.getCurrRoom().addGoldToRewards(100);
                    AbstractDungeon.getCurrRoom().eliteTrigger = true;
                    AbstractDungeon.lastCombatMetricKey = "Gremlin Nob";
                    this.imageEventText.clearAllDialogs();
                    this.enterCombatFromImage();
                    return;
                case 2:
                    logMetric(ID, "Fight");
                    this.screen = CurScreen.FIGHT;
                    //SlimeboundMod.logger.info("fight");
                    AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Colosseum Slavers");
                    AbstractDungeon.getCurrRoom().rewards.clear();
                    AbstractDungeon.getCurrRoom().addRelicToRewards(new WristBlade());
                    AbstractDungeon.getCurrRoom().addGoldToRewards(100);
                    AbstractDungeon.getCurrRoom().eliteTrigger = true;
                    AbstractDungeon.lastCombatMetricKey = "Colosseum Slavers";
                    this.imageEventText.clearAllDialogs();
                    this.enterCombatFromImage();
                    return;
                case 3:
                    this.screen = CurScreen.RESULT;
                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(OPTIONS[3]);
                    logMetricIgnored(ID);
                    return;
                default:
            }
        } else {
            this.openMap();
        }

    }

    private enum CurScreen {
        PRE,
        INTRO,
        FIGHT,
        RESULT;

        CurScreen() {
        }
    }
}
