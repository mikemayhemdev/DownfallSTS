package downfall.events;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.EffectHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import downfall.util.TextureLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class DeadGuy_Evil extends AbstractEvent {
    public static final String ID = "downfall:DeadGuy";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String FIGHT_MSG;
    private static final String ESCAPE_MSG;
    private static final Color DARKEN_COLOR;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        FIGHT_MSG = DESCRIPTIONS[0];
        ESCAPE_MSG = DESCRIPTIONS[1];
        DARKEN_COLOR = new Color(0.5F, 0.5F, 0.5F, 1.0F);
    }

    private int numRewards = 0;
    private int encounterChance = 0;
    private ArrayList<String> rewards = new ArrayList();
    private float x;
    private float y;
    private int enemy;
    private DeadGuy_Evil.CUR_SCREEN screen;
    private Texture adventurerImg;
    private int goldRewardMetric = 0;
    private AbstractRelic relicRewardMetric = null;

    public DeadGuy_Evil() {
        this.x = 800.0F * Settings.scale;
        this.y = AbstractDungeon.floorY;
        this.enemy = 0;
        this.screen = DeadGuy_Evil.CUR_SCREEN.INTRO;
        this.rewards.add("GOLD");
        this.rewards.add("NOTHING");
        this.rewards.add("RELIC");
        Collections.shuffle(this.rewards, new Random(AbstractDungeon.miscRng.randomLong()));
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.encounterChance = 35;
        } else {
            this.encounterChance = 25;
        }

        this.enemy = AbstractDungeon.miscRng.random(0, 2);
        this.adventurerImg = TextureLoader.getTexture("images/npcs/nopants.png");
        this.body = DESCRIPTIONS[2];
        switch (this.enemy) {
            case 0:
                this.body = this.body + DESCRIPTIONS[3];
                break;
            case 1:
                this.body = this.body + DESCRIPTIONS[4];
                break;
            default:
                this.body = this.body + DESCRIPTIONS[5];
        }

        this.body = this.body + DESCRIPTIONS[6];

        if (!this.hasDialog) {
            this.roomEventText.addDialogOption(OPTIONS[0] + this.encounterChance + OPTIONS[4]);
            this.roomEventText.addDialogOption(OPTIONS[1]);
        }

        this.hasDialog = true;
        this.hasFocus = true;
    }

    public void update() {
        super.update();
        if (AbstractDungeon.getCurrRoom().phase != RoomPhase.EVENT && AbstractDungeon.getCurrRoom().phase != RoomPhase.COMPLETE) {
            this.imgColor = DARKEN_COLOR;
        } else {
            this.imgColor = Color.WHITE.cpy();
        }

        if (!RoomEventDialog.waitForInput) {
            this.buttonEffect(this.roomEventText.getSelectedOption());
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        if (AbstractDungeon.miscRng.random(0, 99) < this.encounterChance) {
                            this.screen = DeadGuy_Evil.CUR_SCREEN.FAIL;
                            this.roomEventText.updateBodyText(FIGHT_MSG);
                            this.roomEventText.updateDialogOption(0, OPTIONS[2]);
                            this.roomEventText.removeDialogOption(1);
                            if (Settings.isDailyRun) {
                                AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(30));
                            } else {
                                AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(25, 35));
                            }

                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(this.getMonster());
                            AbstractDungeon.getCurrRoom().eliteTrigger = true;
                        } else {
                            this.randomReward();
                        }

                        return;
                    case 1:
                        this.screen = DeadGuy_Evil.CUR_SCREEN.ESCAPE;
                        this.roomEventText.updateBodyText(ESCAPE_MSG);
                        this.roomEventText.updateDialogOption(0, OPTIONS[1]);
                        this.roomEventText.removeDialogOption(1);
                        return;
                    default:
                        return;
                }
            case SUCCESS:
                this.openMap();
                break;
            case FAIL:

                for (String s : this.rewards) {
                    if (s.equals("GOLD")) {
                        AbstractDungeon.getCurrRoom().addGoldToRewards(30);
                    } else if (s.equals("RELIC")) {
                        AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractDungeon.returnRandomRelicTier());
                    }
                }

                this.enterCombat();
                AbstractDungeon.lastCombatMetricKey = this.getMonster();
                ++this.numRewards;
                logMetric(this.numRewards);
                break;
            case ESCAPE:
                logMetric(this.numRewards);
                this.openMap();
                break;
            default:

        }

    }

    private String getMonster() {
        switch (this.enemy) {
            case 0:
                return "3 Sentries";
            case 1:
                return "Gremlin Nob";
            default:
                return "Lagavulin Event";
        }
    }

    private void randomReward() {
        ++this.numRewards;
        this.encounterChance += 25;

        switch (this.rewards.remove(0)) {
            case "GOLD":
                this.roomEventText.updateBodyText(DESCRIPTIONS[7]);
                EffectHelper.gainGold(AbstractDungeon.player, this.x, this.y, 30);
                AbstractDungeon.player.gainGold(30);
                this.goldRewardMetric = 30;

                break;
            case "NOTHING":
                this.roomEventText.updateBodyText(DESCRIPTIONS[8]);
                break;
            case "RELIC":
                this.roomEventText.updateBodyText(DESCRIPTIONS[9]);
                AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                this.relicRewardMetric = r;
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.x, this.y, r);
                break;
            default:

        }

        if (this.numRewards == 3) {
            logMetric(this.numRewards);
            this.roomEventText.updateBodyText(DESCRIPTIONS[10]);
            this.roomEventText.updateDialogOption(0, OPTIONS[1]);
            this.roomEventText.removeDialogOption(1);
            this.screen = DeadGuy_Evil.CUR_SCREEN.SUCCESS;
        } else {

            this.roomEventText.updateDialogOption(0, OPTIONS[3] + this.encounterChance + OPTIONS[4]);
            this.roomEventText.updateDialogOption(1, OPTIONS[1]);
            this.screen = DeadGuy_Evil.CUR_SCREEN.INTRO;
        }

    }
    public void logMetric(int numAttempts) {
        if (this.relicRewardMetric != null) {
            AbstractEvent.logMetricGainGoldAndRelic(ID, "Searched '" + numAttempts + "' times", this.relicRewardMetric, this.goldRewardMetric);

        } else {

            AbstractEvent.logMetricGainGold(ID, "Searched '" + numAttempts + "' times", this.goldRewardMetric);
        }
    }
    public void render(SpriteBatch sb) {
        super.render(sb);
        sb.setColor(Color.WHITE);
        sb.draw(this.adventurerImg, this.x - 146.0F, this.y - 86.5F, 146.0F, 86.5F, 292.0F, 173.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 292, 173, false, false);
    }

    public void dispose() {
        super.dispose();
        if (this.adventurerImg != null) {
            this.adventurerImg.dispose();
            this.adventurerImg = null;
        }

    }

    private enum CUR_SCREEN {
        INTRO,
        FAIL,
        SUCCESS,
        ESCAPE;

        CUR_SCREEN() {
        }
    }
}
