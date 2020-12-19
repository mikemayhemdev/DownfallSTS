package charbosses.bosses.Defect;

import charbosses.BossMechanicDisplayPanel;
import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.NewAge.ArchetypeAct1StreamlineNewAge;
import charbosses.bosses.Defect.NewAge.ArchetypeAct2ClawNewAge;
import charbosses.cards.anticards.Debug;
import charbosses.core.EnemyEnergyManager;
import charbosses.monsters.BronzeOrbWhoReallyLikesDefectForSomeReason;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;
import guardian.cards.AncientConstruct;
import guardian.powers.ConstructPower;
import slimebound.SlimeboundMod;

public class CharBossDefect extends AbstractCharBoss {
    public static final String ID = downfallMod.makeID("Defect");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Defect").NAMES[0];

    public int clawsPlayed = 0;

    public CharBossDefect() {
        super(NAME, ID, 75, 0.0F, -5.0F, 240.0F, 244.0F, null, 0.0f, -20.0f, AbstractPlayer.PlayerClass.DEFECT);
        this.energyOrb = new EnergyOrbBlue();
        this.energy = new EnemyEnergyManager(3);
        this.loadAnimation("images/characters/defect/idle/skeleton.atlas", "images/characters/defect/idle/skeleton.json", 1.0F);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        e.setTimeScale(0.9f);
        this.energyString = "[B]";

        this.masterMaxOrbs = 3;
        this.maxOrbs = 3;
        type = EnemyType.BOSS;
    }

    @Override
    public AbstractCard anticard() {
        return new Debug();
    }


    @Override
    public void generateDeck() {
        AbstractBossDeckArchetype archetype;

        if (downfallMod.overrideBossDifficulty) {
            archetype = new ArchetypeAct1StreamlineNewAge();
            this.currentHealth -= 100;
            downfallMod.overrideBossDifficulty = false;
        } else
            switch (AbstractDungeon.actNum) {
                case 1:
                    archetype = new ArchetypeAct1StreamlineNewAge();
                    break;
                case 2:
                    archetype = new ArchetypeAct2ClawNewAge();
                    break;
                case 3:
                    archetype = new ArchetypeAct3Orbs();
                    break;
                case 4: {
                    SlimeboundMod.logger.info("Defect spawned at Archetype " + NeowBoss.Rezzes);
                    switch (NeowBoss.Rezzes) {

                        case 1:
                            archetype = new ArchetypeAct1StreamlineNewAge();
                            break;
                        case 2:
                            archetype = new ArchetypeAct2ClawNewAge();
                            break;
                        case 3:
                            archetype = new ArchetypeAct3Orbs();
                            break;
                        default:
                            archetype = new ArchetypeAct3Orbs();
                            break;
                    }
                    break;
                }
                default:
                    archetype = new ArchetypeAct1StreamlineNewAge();
                    break;
            }

        archetype.initialize();
        chosenArchetype = archetype;
//        if (AbstractDungeon.ascensionLevel >= 19) {
//            archetype.initializeBonusRelic();
//        }
    }

    @Override
    public void die() {
        super.die();
        downfallMod.saveBossFight(CharBossDefect.ID);
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        if (chosenArchetype instanceof ArchetypeAct2ClawNewAge) {
            AbstractCreature p = AbstractCharBoss.boss;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ConstructPower(p, p, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new BronzeOrbWhoReallyLikesDefectForSomeReason(-300, 200, 0), true));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new BronzeOrbWhoReallyLikesDefectForSomeReason(200, 130, 1), true));
        }
    }
}