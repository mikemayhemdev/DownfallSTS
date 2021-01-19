package automaton.events;


import automaton.cards.DecaBeam;
import automaton.cards.DonuBeam;
import automaton.util.DecaBeamReward;
import automaton.util.DonuBeamReward;
import automaton.util.ProtoDeca;
import automaton.util.ProtoDonu;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.beyond.Deca;
import com.megacrit.cardcrawl.monsters.beyond.Donu;
import com.megacrit.cardcrawl.monsters.beyond.Spiker;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.ChampionsBelt;
import com.megacrit.cardcrawl.relics.CloakClasp;
import com.megacrit.cardcrawl.relics.WristBlade;
import slimebound.SlimeboundMod;

public class AncientFactory extends AbstractImageEvent {
    public static final String ID = "bronze:AncientFactory";
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

    public AncientFactory() {
        super(NAME, DESCRIPTIONS[0], "bronzeResources/images/events/ancientfactory.png");

        this.screen = CurScreen.INTRO;
        this.imageEventText.clearAllDialogs();
        this.imageEventText.setDialogOption(OPTIONS[0], new DonuBeam());
        this.imageEventText.setDialogOption(OPTIONS[1], new DecaBeam());
        this.imageEventText.setDialogOption(OPTIONS[2]);
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    protected void buttonEffect(int buttonPressed) {
        AbstractMonster m = null;

        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.screen = CurScreen.FIGHT;
                        //SlimeboundMod.logger.info("fight");
                        m = new ProtoDonu();
                        m.maxHealth = m.maxHealth / 2;
                        m.currentHealth = m.maxHealth;
                        m.powers.add(new StrengthPower(m,-3));
                        AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(m);
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().rewards.add(new DonuBeamReward());
                        AbstractDungeon.getCurrRoom().addGoldToRewards(100);
                        AbstractDungeon.getCurrRoom().eliteTrigger = true;
                        this.imageEventText.clearAllDialogs();
                        this.enterCombatFromImage();

                        return;
                    case 1:

                        this.screen = CurScreen.FIGHT;
                        //SlimeboundMod.logger.info("fight");
                        m = new ProtoDeca();
                        m.maxHealth = m.maxHealth / 2;
                        m.currentHealth = m.maxHealth;
                        m.powers.add(new StrengthPower(m,-3));
                        AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(m);
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().rewards.add(new DecaBeamReward());
                        AbstractDungeon.getCurrRoom().addGoldToRewards(100);
                        AbstractDungeon.getCurrRoom().eliteTrigger = true;
                        this.imageEventText.clearAllDialogs();
                        this.enterCombatFromImage();
                        return;
                    case 2:
                        this.screen = CurScreen.FIGHT;
                        //SlimeboundMod.logger.info("fight");
                        m = new ProtoDonu();
                        m.maxHealth = m.maxHealth / 2;
                        m.currentHealth = m.maxHealth;
                        m.powers.add(new StrengthPower(m,-3));
                        AbstractMonster m2 = new ProtoDeca();
                        m2.maxHealth = m2.maxHealth / 2;
                        m2.currentHealth = m2.maxHealth;
                        m2.powers.add(new StrengthPower(m2,-3));
                        AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new AbstractMonster[] { m, m2 });
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().rewards.add(new DonuBeamReward());
                        AbstractDungeon.getCurrRoom().rewards.add(new DecaBeamReward());
                        AbstractDungeon.getCurrRoom().addGoldToRewards(100);
                        AbstractDungeon.getCurrRoom().eliteTrigger = true;
                        this.imageEventText.clearAllDialogs();
                        this.enterCombatFromImage();
                        return;
                    case 3:
                        this.screen = CurScreen.RESULT;
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[3]);
                        return;
                    default:
                        return;
                }

            default:
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
