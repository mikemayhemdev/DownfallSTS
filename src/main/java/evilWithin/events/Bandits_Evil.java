package evilWithin.events;

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
import evilWithin.vfx.StealRelicEffect;

import java.util.ArrayList;

public class Bandits_Evil extends AbstractEvent {
    public static final String ID = "evilWithin:Bandits";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private CUR_SCREEN screen;
    private AbstractRelic wantThisOne = null;

    public Bandits_Evil() {
        this.screen = CUR_SCREEN.INTRO;
        this.body = DESCRIPTIONSALT[0];// 35
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
        this.hasDialog = true;// 40
        this.hasFocus = true;// 41
        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Masked Bandits");// 42
    }

    public void update() {
        super.update();
        if (!RoomEventDialog.waitForInput) {// 48
            this.buttonEffect(this.roomEventText.getSelectedOption());// 49
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {// 57
                    case 0:
                        AbstractMonster q = AbstractDungeon.getCurrRoom().monsters.getMonster("BanditLeader");
                        AbstractDungeon.effectList.add(new StealRelicEffect(AbstractDungeon.player.getRelic(wantThisOne.relicId), q));
                        AbstractDungeon.player.loseRelic(wantThisOne.relicId);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, new Circlet());
                        this.roomEventText.updateBodyText(DESCRIPTIONSALT[1]);// 61
                        this.roomEventText.updateDialogOption(0, OPTIONS[3]);// 62
                        this.roomEventText.clearRemainingOptions();// 63
                        this.screen = CUR_SCREEN.COMPLETE;
                        return;// 65
                    case 1:
                        logMetric("Masked Bandits", "Fought Bandits");// 68
                        if (Settings.isDailyRun) {// 70
                            AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(30));// 71
                        } else {
                            AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(25, 35));// 73
                        }

                        if (AbstractDungeon.player.hasRelic(RedMask.ID)) {// 75
                            AbstractDungeon.getCurrRoom().addRelicToRewards(new Circlet());// 76
                        } else {
                            AbstractDungeon.getCurrRoom().addRelicToRewards(new RedMask());// 78
                        }

                        this.enterCombat();// 81
                        AbstractDungeon.lastCombatMetricKey = "Masked Bandits";// 82
                        return;// 83
                    default:
                        return;// 106
                }
            case COMPLETE:
                this.openMap();
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Masked Bandits");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
    }

    private static enum CUR_SCREEN {
        INTRO,
        COMPLETE;

        private CUR_SCREEN() {
        }
    }
}
