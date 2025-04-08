package sneckomod.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.city.Centurion;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.cards.curses.Bewildered;
import sneckomod.relics.BabySnecko;

public class SuspiciousHouse extends AbstractImageEvent {
    public static final String ID = "sneckomod:SuspiciousHouse";
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
    private boolean postFightTriggered = false;

    public SuspiciousHouse() {
        super(NAME, DESCRIPTIONS[0], "sneckomodResources/images/events/cityStreet.png");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;

        this.imageEventText.setDialogOption(OPTIONS[0], new BabySnecko());
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.imageEventText.clearAllDialogs();
                switch (buttonPressed) {
                    case 0:
                        BabySnecko relic = new BabySnecko();
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), relic);
                        this.screen = CurScreen.COMBAT;

                        AbstractMonster m = new Centurion(0, 0);
                        if (AbstractDungeon.ascensionLevel >= 15) {
                            m.powers.add(new StrengthPower(m, 3));
                        }
                        AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(m);

                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().rewardAllowed = false;
                        AbstractDungeon.lastCombatMetricKey = "Angry Centurion";
                        this.enterCombatFromImage();
                        break;
                    case 1:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.screen = CurScreen.END;
                        logMetricIgnored(ID);
                        return;
                }
                break;

            case POSTFIGHT:
                this.imageEventText.clearAllDialogs();
              //  this.imageEventText.setDialogOption(OPTIONS[1]);
              //  this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
              //   this.screen = CurScreen.END;
                return;

            case END:
                this.openMap();
                break;
        }
    }

    @Override
    public void update() {
        super.update();
        if (this.screen == CurScreen.COMBAT && AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead() && !postFightTriggered) {
            postFightTriggered = true;
            this.screen = CurScreen.POSTFIGHT;
            this.imageEventText.clearAllDialogs();
            AbstractDungeon.getCurrRoom().rewards.clear();
            AbstractDungeon.getCurrRoom().rewardAllowed = false;
            AbstractDungeon.resetPlayer();
            AbstractDungeon.player.drawX = (float) Settings.WIDTH * 0.25F;
            AbstractDungeon.player.preBattlePrep();
            this.imageEventText.clearAllDialogs();
            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
            this.imageEventText.setDialogOption(OPTIONS[1]);
            this.enterImageFromCombat();
            this.screen = CurScreen.END;
        }
    }

    @Override
    public void reopen() {
        if (this.screen == CurScreen.POSTFIGHT) {
            AbstractDungeon.getCurrRoom().rewards.clear();
            AbstractDungeon.getCurrRoom().rewardAllowed = false;
            AbstractDungeon.resetPlayer();
            AbstractDungeon.player.drawX = (float) Settings.WIDTH * 0.25F;
            AbstractDungeon.player.preBattlePrep();
            this.imageEventText.clearAllDialogs();
            //this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
            //this.imageEventText.setDialogOption(OPTIONS[1]);
            this.enterImageFromCombat();
        }
    }

    private enum CurScreen {
        INTRO,
        COMBAT,
        POSTFIGHT,
        END;
    }
}
