package automaton.events;


import automaton.cards.DazingPulse;
import automaton.cards.Explode;
import automaton.cards.Spike;
import automaton.util.DazingPulseReward;
import automaton.util.DonuBeamReward;
import automaton.util.ExplodeReward;
import automaton.util.SpikeReward;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.beyond.Exploder;
import com.megacrit.cardcrawl.monsters.beyond.Repulsor;
import com.megacrit.cardcrawl.monsters.beyond.Spiker;
import com.megacrit.cardcrawl.monsters.exordium.SlaverBlue;
import com.megacrit.cardcrawl.relics.ChampionsBelt;
import com.megacrit.cardcrawl.relics.CloakClasp;
import com.megacrit.cardcrawl.relics.WristBlade;
import slimebound.SlimeboundMod;

public class ShapeFactory extends AbstractImageEvent {
    public static final String ID = "bronze:ShapeFactory";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;

    private boolean fightSpiker = false;
    private boolean fightRepulsor = false;
    private boolean fightExploder = false;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurScreen screen;

    public ShapeFactory() {
        super(NAME, DESCRIPTIONS[0], "bronzeResources/images/events/shapefactory.png");

        this.screen = CurScreen.INTRO;
        this.imageEventText.clearAllDialogs();
        this.imageEventText.setDialogOption(OPTIONS[0], new Spike());
        this.imageEventText.setDialogOption(OPTIONS[1], new DazingPulse());
        this.imageEventText.setDialogOption(OPTIONS[2], new Explode());
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.screen = CurScreen.CHOOSETWO;
                        fightSpiker = true;
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[6], new DazingPulse());
                        this.imageEventText.setDialogOption(OPTIONS[7], new Explode());
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        return;
                    case 1:
                        this.screen = CurScreen.CHOOSETWO;
                        fightRepulsor = true;
                        AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new Repulsor(0.0F, 0.0F));
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[5], new Spike());
                        this.imageEventText.setDialogOption(OPTIONS[7], new Explode());
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        return;
                    case 2:
                        this.screen = CurScreen.CHOOSETWO;
                        fightExploder = true;
                        AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new Exploder(0.0F, 0.0F));
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[5], new Spike());
                        this.imageEventText.setDialogOption(OPTIONS[6], new DazingPulse());
                        this.imageEventText.setDialogOption(OPTIONS[4]);
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
            case CHOOSETWO:
                switch (buttonPressed) {
                    case 0:
                        this.screen = CurScreen.CHOOSETHREE;
                        if (fightSpiker) {
                            fightRepulsor = true;
                        }
                        else fightSpiker = true;
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        this.imageEventText.clearAllDialogs();
                        if (!fightSpiker) this.imageEventText.setDialogOption(OPTIONS[5], new Spike());
                        else if (!fightRepulsor) this.imageEventText.setDialogOption(OPTIONS[6], new DazingPulse());
                        else if (!fightExploder) this.imageEventText.setDialogOption(OPTIONS[7], new Explode());

                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        return;
                    case 1:
                        this.screen = CurScreen.CHOOSETHREE;
                        if (fightExploder) {
                            fightRepulsor = true;
                        }
                        else fightExploder = true;
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        this.imageEventText.clearAllDialogs();
                        if (!fightSpiker) this.imageEventText.setDialogOption(OPTIONS[5], new Spike());
                        else if (!fightRepulsor) this.imageEventText.setDialogOption(OPTIONS[6], new DazingPulse());
                        else if (!fightExploder) this.imageEventText.setDialogOption(OPTIONS[7], new Explode());

                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        return;
                    case 2:
                        beginFight();
                        return;
                    default:
                        return;
                }
            case CHOOSETHREE:
                switch (buttonPressed) {
                    case 0:
                        fightRepulsor = true;
                        fightExploder = true;
                        fightSpiker = true;
                        beginFight();
                        return;
                    case 1:
                        beginFight();
                        return;
                    default:
                        return;
                }
            default:
                this.openMap();
        }

    }

    private void beginFight(){
        this.screen = CurScreen.FIGHT;
        //SlimeboundMod.logger.info("fight");
        float spawnX = 0F;

        AbstractDungeon.getCurrRoom().rewards.clear();
        if (fightSpiker){
            AbstractDungeon.getCurrRoom().rewards.add(new SpikeReward());
        }
        if (fightRepulsor){
            AbstractDungeon.getCurrRoom().rewards.add(new DazingPulseReward());
        }
        if (fightExploder){
            AbstractDungeon.getCurrRoom().rewards.add(new ExplodeReward());
        }

        if (fightSpiker){
            AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new Spiker(spawnX, 0.0F));
            spawnX -= 225F;
            if (fightRepulsor){
                AbstractDungeon.getCurrRoom().monsters.add(new Repulsor(spawnX, 0.0F));
                spawnX -= 225F;
            }
            if (fightExploder){
                AbstractDungeon.getCurrRoom().monsters.add(new Exploder(spawnX, 0.0F));
            }
        }
        else if (fightRepulsor){
            AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new Repulsor(spawnX, 0.0F));
            spawnX -= 225F;
            if (fightExploder){
                AbstractDungeon.getCurrRoom().monsters.add(new Exploder(spawnX, 0.0F));
            }
        }
        else if (fightExploder){
            AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new Exploder(spawnX, 0.0F));
        }

        this.imageEventText.clearAllDialogs();
        this.enterCombatFromImage();
    }

    private enum CurScreen {
        PRE,
        INTRO,
        CHOOSETWO,
        CHOOSETHREE,
        FIGHT,
        RESULT;

        CurScreen() {
        }
    }
}
