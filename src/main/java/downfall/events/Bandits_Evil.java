package downfall.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.RedMask;
import downfall.downfallMod;
import downfall.relics.RedIOU;
import downfall.vfx.StealRelicEffect;

import java.util.ArrayList;
import java.util.Collections;

public class Bandits_Evil extends AbstractEvent {
    public static final String ID = downfallMod.makeID("Bandits");
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Masked Bandits");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
    }

    private Bandits_Evil.CUR_SCREEN screen;
    private AbstractRelic wantThisOne = null;

    public Bandits_Evil() {
        this.screen = Bandits_Evil.CUR_SCREEN.INTRO;
        this.body = DESCRIPTIONSALT[0];
        AbstractRelic.RelicTier t = AbstractRelic.RelicTier.RARE;
        wantThisOne = null;
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            switch (t) {
                case RARE:
                    if (r.tier == AbstractRelic.RelicTier.COMMON) {
                        t = AbstractRelic.RelicTier.COMMON;
                    } else if (r.tier == AbstractRelic.RelicTier.UNCOMMON) {
                        t = AbstractRelic.RelicTier.UNCOMMON;
                    }
                    break;
                case UNCOMMON:
                    if (r.tier == AbstractRelic.RelicTier.COMMON) {
                        t = AbstractRelic.RelicTier.COMMON;
                    }
                    break;
                default:
                    break;
            }
        }
        ArrayList<AbstractRelic> wantablesList = new ArrayList<>();
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r.tier == t)
                wantablesList.add(r);
        }
        if (!wantablesList.isEmpty()) {
            wantThisOne = wantablesList.get(AbstractDungeon.cardRandomRng.random(wantablesList.size() - 1));
        }
        if (wantThisOne != null) {
            this.roomEventText.addDialogOption(OPTIONSALT[0] + wantThisOne.name + OPTIONSALT[1]);
        } else {
            this.roomEventText.addDialogOption(OPTIONSALT[2], true);
        }
        this.roomEventText.addDialogOption(OPTIONS[1]);
        this.hasDialog = true;
        this.hasFocus = true;
        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Masked Bandits");
    }

    public void update() {
        super.update();
        if (!RoomEventDialog.waitForInput) {
            this.buttonEffect(this.roomEventText.getSelectedOption());
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        AbstractMonster q = AbstractDungeon.getCurrRoom().monsters.getMonster("BanditLeader");
                        AbstractDungeon.effectList.add(new StealRelicEffect(AbstractDungeon.player.getRelic(wantThisOne.relicId), q));
                        AbstractDungeon.player.loseRelic(wantThisOne.relicId);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, new RedIOU());
                        this.roomEventText.updateBodyText(DESCRIPTIONSALT[1]);
                        this.roomEventText.updateDialogOption(0, OPTIONS[3]);
                        this.roomEventText.clearRemainingOptions();
                        this.screen = Bandits_Evil.CUR_SCREEN.COMPLETE;
                        logMetric(ID, "Hired Bandits",
                                null, null, null, null,
                                Collections.singletonList(RedIOU.ID), null, Collections.singletonList(wantThisOne.relicId), 0, 0, 0, 0, 0, 0);
                        return;
                    case 1:
                        logMetric(ID, "Fought Bandits");
                        if (Settings.isDailyRun) {
                            AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(30));
                        } else {
                            AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(25, 35));
                        }

                        if (AbstractDungeon.player.hasRelic(RedMask.ID)) {
                            AbstractDungeon.getCurrRoom().addRelicToRewards(new Circlet());
                        } else {
                            AbstractDungeon.getCurrRoom().addRelicToRewards(new RedMask());
                        }

                        this.enterCombat();
                        AbstractDungeon.lastCombatMetricKey = "Masked Bandits";
                        return;
                    default:
                        return;
                }
            case COMPLETE:
                this.openMap();
        }

    }

    private static enum CUR_SCREEN {
        INTRO,
        COMPLETE;

        CUR_SCREEN() {
        }
    }
}
