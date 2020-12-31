package downfall.events;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.monsters.FaceTrader;
import downfall.relics.CloakOfManyFaces;
import slimebound.SlimeboundMod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FaceTrader_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:FaceTrader";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private static final EventStrings eventStrings;
    private static int goldReward;
    private static int damage;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("FaceTrader");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
    }

    private CurScreen screen;

    public FaceTrader_Evil() {
        super(NAME, DESCRIPTIONS[0], "images/events/facelessTrader.jpg");
        this.screen = CurScreen.INTRO;
        if (AbstractDungeon.ascensionLevel >= 15) {
            goldReward = 50;
        } else {
            goldReward = 75;
        }

        damage = AbstractDungeon.player.maxHealth / 10;
        if (damage == 0) {
            damage = 1;
        }

        this.imageEventText.setDialogOption(OPTIONS[4]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1] + DESCRIPTIONSALT[0]);
                        this.imageEventText.updateDialogOption(0, OPTIONSALT[0]);
                        this.imageEventText.setDialogOption(OPTIONS[2]);
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.screen = CurScreen.MAIN;
                        return;
                    default:
                        return;
                }
            case MAIN:
                switch (buttonPressed) {
                    case 0:

                        this.screen = CurScreen.FIGHT;
                        //SlimeboundMod.logger.info("fight");
                        AbstractDungeon.getCurrRoom().monsters =  MonsterHelper.getEncounter("downfall:FaceTrader");
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addRelicToRewards(new CloakOfManyFaces());
                        AbstractDungeon.getCurrRoom().addGoldToRewards(100);

                        AbstractDungeon.getCurrRoom().eliteTrigger = true;
                        this.imageEventText.clearRemainingOptions();
                        this.enterCombatFromImage();
                        AbstractDungeon.lastCombatMetricKey = "downfall:FaceTrader";
                        break;
                    case 1:
                        AbstractRelic r = this.getRandomFace();
                        logMetricObtainRelic("FaceTrader", "Trade", r);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, r);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.screen = CurScreen.RESULT;
                        break;
                    case 2:
                        this.logMetric("Leave");
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        this.screen = CurScreen.RESULT;
                }
                break;
            default:
                this.openMap();
        }

    }

    private AbstractRelic getRandomFace() {
        ArrayList<String> ids = new ArrayList();
        if (!AbstractDungeon.player.hasRelic("CultistMask")) {
            ids.add("CultistMask");
        }

        if (!AbstractDungeon.player.hasRelic("FaceOfCleric")) {
            ids.add("FaceOfCleric");
        }

        if (!AbstractDungeon.player.hasRelic("GremlinMask")) {
            ids.add("GremlinMask");
        }

        if (!AbstractDungeon.player.hasRelic("NlothsMask")) {
            ids.add("NlothsMask");
        }

        if (!AbstractDungeon.player.hasRelic("SsserpentHead")) {
            ids.add("SsserpentHead");
        }

        if (ids.size() <= 0) {
            ids.add("Circlet");
        }

        Collections.shuffle(ids, new Random(AbstractDungeon.miscRng.randomLong()));
        return RelicLibrary.getRelic(ids.get(0)).makeCopy();
    }

    public void logMetric(String actionTaken) {
        AbstractEvent.logMetric("FaceTrader", actionTaken);
    }

    private enum CurScreen {
        INTRO,
        MAIN,
        FIGHT,
        RESULT;

        CurScreen() {
        }
    }
}
